package com.example.mobile_tourist_guide.data

import kotlinx.serialization.Serializable

@Serializable
data class RoutesPoints(
    val points: List<String>? = null
)