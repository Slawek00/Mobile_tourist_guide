package com.example.mobile_tourist_guide.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

object FirebaseAPI {
    val authFirebase = FirebaseAuth.getInstance()
    val storageFirebase = FirebaseStorage.getInstance()
    val db = Firebase.firestore
}