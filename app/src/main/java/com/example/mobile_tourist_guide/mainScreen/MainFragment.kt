package com.example.mobile_tourist_guide.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.adapters.CardViewAdapter
import com.example.mobile_tourist_guide.databinding.FragmentMainBinding


class MainFragment : Fragment(){
    private lateinit var binding:FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var cardViewAdapter: CardViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)

        cardViewAdapter = CardViewAdapter(requireContext())
        binding.recyclerMainFragment.adapter = cardViewAdapter

        // jak chcemy wpisać wartość w livedata to trzeba użyć obserwatora aby to odebrać
        viewModel.routeList.observe(viewLifecycleOwner){
            cardViewAdapter.submitList(it)
        }

        return binding.root
    }
}