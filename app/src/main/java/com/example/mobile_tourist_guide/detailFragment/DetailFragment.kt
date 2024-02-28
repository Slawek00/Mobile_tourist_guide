package com.example.mobile_tourist_guide.detailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.data.Routes
import com.example.mobile_tourist_guide.data.RoutesPoints
import com.example.mobile_tourist_guide.databinding.FragmentDetailBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var data: Routes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBundle = arguments
        val dataJson = dataBundle!!.getString("DataOfRoute")
        data = Json.decodeFromString(dataJson!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)

        Glide.with(this)
            .load(data.image)
            .into(binding.imageDetail)

        binding.tittleDetail.text = data.name
        binding.priceDetail.text = "Szacowane koszta: ${data.price} zł"
        binding.alertDetail.text = "Uwagi: ${data.alert}"
        binding.desDetail.text = "Opis: ${data.des}"
        binding.lengthDetail.text = "Szacowana długość: ${data.length} km"
        binding.transportDetail.text = "Zalecany sposób przemieszczania się: ${data.transport}"
        binding.ratingBarUsers.rating = data.rating!!

        viewModel.userRating.observe(viewLifecycleOwner){
            val ratingUser: Float? = it.find { it.uid == data.uid }?.rating
            if(ratingUser != null){
                binding.ratingBarUser.rating = ratingUser
                binding.ratingBarUser.setIsIndicator(true)
            }else{
                binding.ratingBarUser.rating = 0F
            }
        }

        binding.deleteRoute.setOnClickListener{
            viewModel.deleteRoute(data.uid!!)
            infoDelete()
        }

        binding.addFavorite.setOnClickListener{
            viewModel.addData(data.uid!!, data.image!!)
            infoAdd()
        }

        binding.start.setOnClickListener {
            val dataMap = Bundle()
            val geoPointsList = RoutesPoints(data.points)
            val geoPointsJson = Json.encodeToString(geoPointsList)
            dataMap.putString("points", geoPointsJson)
            findNavController().navigate(R.id.action_detailFragment_to_mapFragment, dataMap, NavOptions.Builder()
                .setPopUpTo(R.id.detailFragment, true)
                .build())
        }

        binding.userRating.setOnClickListener{
            val userRating = binding.ratingBarUser.rating
            if (userRating != 0f) {
                viewModel.calculateRating(data.uid!!, data.rating!!, userRating, data.count_rating!!)
            }
        }

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
        }

        return binding.root
    }

    private fun infoAdd(){
        val toast = Toast.makeText(requireContext(), "Dodano do ulubionych", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun infoDelete(){
        val toast = Toast.makeText(requireContext(), "Usunięto z ulubionych", Toast.LENGTH_SHORT)
        toast.show()
    }
}