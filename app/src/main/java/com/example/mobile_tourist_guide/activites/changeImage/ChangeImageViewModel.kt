package com.example.mobile_tourist_guide.activites.changeImage

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.repository.FirebaseRepository

class ChangeImageViewModel: ViewModel() {
    private val firebaseRepository = FirebaseRepository()

    fun newImage(uri: Uri?){
        firebaseRepository.uploadImage(uri)
    }
}