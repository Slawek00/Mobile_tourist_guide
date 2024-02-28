package com.example.mobile_tourist_guide.profileScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.abstractClasses.BaseFragment
import com.example.mobile_tourist_guide.adapters.FavoriteAdapter
import com.example.mobile_tourist_guide.adapters.HobbyUserAdapter
import com.example.mobile_tourist_guide.databinding.FragmentProfileBinding


class ProfileFragment : BaseFragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var hobbyAdapter: HobbyUserAdapter
    private lateinit var favAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        hobbyAdapter = HobbyUserAdapter()
        favAdapter = FavoriteAdapter(requireContext())
        binding.recyclerUserHobby.adapter = hobbyAdapter
        binding.recyclerFavorite.adapter = favAdapter

        viewModel.userProfile.observe(viewLifecycleOwner){
            if(it.image_src != null){
                loadImage(it.image_src)
            }else{
                loadURIImage()
            }
            binding.userEmail.text = it.email
            binding.userName.text = it.name
            hobbyAdapter.submitList(it.hobbys!!)
        }

        viewModel.userFavRoutes.observe(viewLifecycleOwner){
            favAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }
    }

    private fun loadImage(url: String){
        Glide.with(this)
            .load(url)
            .circleCrop()
            .into(binding.profileImage)
    }

    private fun loadURIImage(){
        Glide.with(this)
            .load(R.drawable.anchor)
            .circleCrop()
            .into(binding.profileImage)
    }
}