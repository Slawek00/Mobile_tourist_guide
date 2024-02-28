package com.example.mobile_tourist_guide.data

data class User(
    val uid: String? = null,
    val name: String? = null,
    val hobbys: List<String>? = null,
    val email: String? = null,
    val image_src: String? = null,
    val language: String? = null)