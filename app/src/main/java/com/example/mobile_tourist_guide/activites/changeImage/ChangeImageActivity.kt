package com.example.mobile_tourist_guide.activites.changeImage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.activites.main.MainActivity
import com.example.mobile_tourist_guide.databinding.ActivityChangeImageBinding

class ChangeImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeImageBinding
    private val viewModel: ChangeImageViewModel by viewModels()
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_image)

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if(it != null) {
                uri = it
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(binding.imageProfileUpdate)
            }
        }

        binding.imageProfileUpdate.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.repButtonChangeImage.setOnClickListener{
            viewModel.newImage(uri)
            val intent = Intent(this@ChangeImageActivity, MainActivity::class.java).apply{
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            startActivity(intent)
        }
    }
}