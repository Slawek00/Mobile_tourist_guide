package com.example.mobile_tourist_guide.profileScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.data.FavoriteRoutes
import com.example.mobile_tourist_guide.data.User
import com.example.mobile_tourist_guide.repository.FirebaseRepository

class ProfileViewModel: ViewModel() {
    private val firebaseRepo = FirebaseRepository()
    private val _userProfile = firebaseRepo.getUserProfile()
    private val _userFavRoutes = firebaseRepo.getFavRoutes()
    val userProfile: LiveData<User>
        get() = _userProfile

    val userFavRoutes: LiveData<List<FavoriteRoutes>>
        get() = _userFavRoutes
}