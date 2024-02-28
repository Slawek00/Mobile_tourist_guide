package com.example.mobile_tourist_guide.data

import kotlinx.serialization.Serializable

@Serializable
data class Routes(
    val uid: String? = null,
    val name: String? = null,
    val des: String? = null,
    val price: String? = null,
    val image: String? = null,
    val alert: String? = null,
    val rating: Float? = null,
    val length: String? = null,
    val tag: String? = null,
    val points: List<String>? = null,
    val transport: String? = null,
    val count_rating: Int? = null,
)
