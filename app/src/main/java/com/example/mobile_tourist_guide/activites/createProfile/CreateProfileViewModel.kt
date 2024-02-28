package com.example.mobile_tourist_guide.activites.createProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.data.Hobbys
import com.example.mobile_tourist_guide.repository.FirebaseRepository

class CreateProfileViewModel: ViewModel() {
    private val firebaseRepo = FirebaseRepository()
    //LiveData to wartość tylko do odczytu! MutableLiveData ma możliwość edycji!
    private var _hobby: LiveData<List<Hobbys>> = firebaseRepo.getAllHobbys()
    val hobby: LiveData<List<Hobbys>>
        get() = _hobby

    val hobbySelected = mutableListOf<Hobbys>()

    fun uploadImage(uri: Uri?){
        firebaseRepo.uploadImage(uri)
    }

    fun uploadGetHobbys(){
        for(element in hobbySelected){
            firebaseRepo.addUserHobbys(element)
        }
    }

}