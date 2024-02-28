package com.example.mobile_tourist_guide.activites.createProfile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.activites.main.MainActivity
import com.example.mobile_tourist_guide.adapters.HobbysAdapter
import com.example.mobile_tourist_guide.databinding.ActivityCreateProfileBinding
import com.google.android.material.snackbar.Snackbar

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateProfileBinding
    private val viewModel: CreateProfileViewModel by viewModels()
    private lateinit var recyclerAdapter: HobbysAdapter
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile)

        recyclerAdapter = HobbysAdapter()
        binding.recyclerViewCreateProfile.adapter = recyclerAdapter

        viewModel.hobby.observe(this) {
            recyclerAdapter.submitList(it)
        }

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if(it != null) {
                uri = it
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(binding.imageProfileCreate)
            }
        }

        binding.repButton.setOnClickListener {view ->
            run {
                acceptButtonClick(view)
            }
        }

        binding.imageProfileCreate.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


    }


    private fun acceptButtonClick(view: View){
        if(recyclerAdapter.returnList().isNotEmpty()){
            if(uri!=null){
                viewModel.uploadImage(uri)
            }
            viewModel.hobbySelected.addAll(recyclerAdapter.returnList())
            viewModel.uploadGetHobbys()
            goToActivity()
            recyclerAdapter.clearList()
        }else{
            info(view)
        }
    }


    private fun info(view: View){
        Snackbar.make(view, "Musisz wybrać chociaż jedno zainteresowanie!",
            Snackbar.LENGTH_SHORT).show()
    }


    private fun goToActivity(){
        val intent = Intent(this@CreateProfileActivity, MainActivity::class.java).apply{
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        startActivity(intent)
    }
}