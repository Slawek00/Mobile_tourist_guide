package com.example.mobile_tourist_guide.settingsFragment

import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.repository.FirebaseRepository

class SettingsViewModel: ViewModel() {
    private val firebaseRepo = FirebaseRepository()

    fun logout(){
        firebaseRepo.logoutUser()
    }

    fun delete(){
        firebaseRepo.deleteUser()
    }

    fun updateName(userName: String){
        firebaseRepo.changeUserName(userName)
    }

    fun report(report: String){
        firebaseRepo.report(report)
    }
}