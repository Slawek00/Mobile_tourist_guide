package com.example.mobile_tourist_guide.loginScreen


import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    fun dataIsNotEmpty(email: String, password: String): Boolean{
        return email.isNullOrEmpty() || password.isNullOrEmpty()
    }
}