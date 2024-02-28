package com.example.mobile_tourist_guide.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobile_tourist_guide.data.FavoriteRoutes
import com.example.mobile_tourist_guide.data.Hobbys
import com.example.mobile_tourist_guide.data.Routes
import com.example.mobile_tourist_guide.data.User
import com.example.mobile_tourist_guide.data.UserRating
import com.example.mobile_tourist_guide.network.FirebaseAPI
import com.google.firebase.firestore.FieldValue
import com.google.firebase.storage.StorageReference


class FirebaseRepository {
    private val TAG = "MTG"

    fun logoutUser(){
        FirebaseAPI.authFirebase.signOut()
    }

    fun changeUserName(newName: String){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!

        FirebaseAPI.db.collection("users_profile")
            .document(user)
            .update("name", newName)
            .addOnSuccessListener {
                Log.d(TAG, "User name updated")
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
    }

    fun report(report: String){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!

        val newReport = hashMapOf(
            "uid" to user,
            "report" to report
        )

        FirebaseAPI.db.collection("report")
            .document(user)
            .set(newReport)
    }

    fun deleteUser(){
        val user = FirebaseAPI.authFirebase.currentUser

        user!!.delete()
            .addOnSuccessListener {
                Log.d(TAG, "User Deleted")
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }

        logoutUser()
    }

    fun insertProfileUser(user: User){
        FirebaseAPI.db.collection("users_profile")
            .document(user.uid!!)
            .set(user)
    }

    fun addUserRating(uidRoute: String, rating: Float){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        val data = hashMapOf(
            "uid" to uidRoute,
            "rating" to rating
        )
        FirebaseAPI.db.collection("users_profile")
            .document(user)
            .collection("user_rating")
            .add(data)
            .addOnFailureListener {
                Log.d(TAG, "User rating created")
            }
            .addOnFailureListener{
                Log.d(TAG, it.message.toString())
            }
    }


    fun getRatingUser():LiveData<List<UserRating>>{
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        val result = MutableLiveData<List<UserRating>>()
        val docRef = FirebaseAPI.db.collection("users_profile")
            .document(user)
            .collection("user_rating")

        docRef.addSnapshotListener { value, error ->
            if(error != null){
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }


            val resultObjects = value?.toObjects(UserRating::class.java)
            result.postValue(resultObjects!!)
        }

        return result
    }

    fun deleteRoute(uid: String){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        FirebaseAPI.db.collection("users_profile")
            .document(user)
            .collection("favoriteRoutes")
            .document(uid)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Delete document")
            }
            .addOnFailureListener{
                Log.e(TAG, it.message.toString())
            }
    }

    fun getUserProfile():LiveData<User>{
        val result = MutableLiveData<User>()
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        val docRef = FirebaseAPI.db.collection("users_profile").document(user)

        docRef.addSnapshotListener { value, error ->
            if(error != null){
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }
            val resultObject = value?.toObject(User::class.java)
            result.postValue(resultObject!!)
        }
        return result
    }

    fun addFavRoute(uid: String, imageURL: String){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        val data = hashMapOf(
            "uid" to uid,
            "image_src" to imageURL
        )
        FirebaseAPI.db.collection("users_profile")
            .document(user)
            .collection("favoriteRoutes")
            .document(uid)
            .set(data)
            .addOnSuccessListener {
                Log.d(TAG, "Fav route add")
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }

    }

    fun updateCountRating(uid: String, countRating: Int){
        FirebaseAPI.db.collection("routes")
            .document(uid)
            .update("count_rating",countRating)
            .addOnSuccessListener {
                Log.d(TAG, "Count rating update")
            }
            .addOnFailureListener{
                Log.e(TAG, it.message.toString())
            }
    }

    fun updateRating(uid: String, rating: Float){
        FirebaseAPI.db.collection("routes")
            .document(uid)
            .update("rating", rating)
            .addOnSuccessListener {
                Log.d(TAG, "Rating update")
            }
            .addOnFailureListener{
                Log.e(TAG, it.message.toString())
            }
    }

    fun getFavRoutes():LiveData<List<FavoriteRoutes>>{
        val user= FirebaseAPI.authFirebase.currentUser?.uid!!
        val result = MutableLiveData<List<FavoriteRoutes>>()
        val docRef = FirebaseAPI.db.collection("users_profile")
            .document(user)
            .collection("favoriteRoutes")

        docRef.addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            val resultObject = value?.toObjects(FavoriteRoutes::class.java)
            result.postValue(resultObject!!)

        }
        return result
    }

    fun addUserHobbys(hobby: Hobbys){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        FirebaseAPI.db.collection("users_profile")
            .document(user)
            .update("hobbys", FieldValue.arrayUnion(hobby.name))
            .addOnSuccessListener {
                Log.d(TAG, "Hobby add")
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
    }

    fun getAllRoutes():LiveData<List<Routes>>{
        val result = MutableLiveData<List<Routes>>()
        val docRef = FirebaseAPI.db.collection("routes")
        docRef.addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            val resultObject = value?.toObjects(Routes::class.java)
            result.postValue(resultObject!!)
        }
        return result
    }

    fun getAllHobbys():LiveData<List<Hobbys>>{
        val result = MutableLiveData<List<Hobbys>>()
        val docRef = FirebaseAPI.db.collection("hobbys")
        docRef.addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            val resultObject = value?.toObjects(Hobbys::class.java)
            result.postValue(resultObject!!)

        }
        return result
    }


    fun uploadImage(imageUri: Uri?){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        val storageReference =
            FirebaseAPI.storageFirebase.getReference("users/$user")
        storageReference.putFile(imageUri!!)
            .addOnCompleteListener{
                Log.d(TAG, "Image uploaded")
            }
            .addOnSuccessListener {
                getURL(it.storage)
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
    }

    private fun getURL(storage: StorageReference){
        storage.downloadUrl
            .addOnSuccessListener {
                uploadProfileImageURL(it.toString())
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
    }

    private fun uploadProfileImageURL(url: String?){
        val user = FirebaseAPI.authFirebase.currentUser?.uid!!
        FirebaseAPI.db.collection("users_profile")
            .document(user)
            .update("image_src", url)
            .addOnSuccessListener {
                Log.d(TAG, "Image src updated")
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
    }
}