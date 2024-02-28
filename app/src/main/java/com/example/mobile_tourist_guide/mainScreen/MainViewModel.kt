package com.example.mobile_tourist_guide.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.data.Routes
import com.example.mobile_tourist_guide.repository.FirebaseRepository

class MainViewModel: ViewModel() {
    private val repo = FirebaseRepository()
    private val _routeList: LiveData<List<Routes>> = repo.getAllRoutes()

    val routeList: LiveData<List<Routes>>
        get() = _routeList
}