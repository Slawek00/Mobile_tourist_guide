package com.example.mobile_tourist_guide.detailFragment


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.data.UserRating
import com.example.mobile_tourist_guide.repository.FirebaseRepository

class DetailViewModel: ViewModel() {
    private val repo = FirebaseRepository()
    private val _userRating: LiveData<List<UserRating>> = repo.getRatingUser()

    val userRating: LiveData<List<UserRating>>
        get() = _userRating

    fun addData(uid: String, imageURL: String){
        repo.addFavRoute(uid, imageURL)
    }

    private fun addUserRate(uid: String, rating: Float){
        repo.addUserRating(uid, rating)
    }

    fun deleteRoute(uid:String){
        repo.deleteRoute(uid)
    }

    fun calculateRating(uid: String, oldRating: Float ,rating:Float, countRating: Int){
        val newCountRating = countRating+1
        val newRating = (oldRating+rating)/newCountRating
        repo.updateRating(uid, newRating)
        repo.updateCountRating(uid, newCountRating)
        addUserRate(uid, rating)
    }
}