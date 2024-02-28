package com.example.mobile_tourist_guide.abstractClasses


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mobile_tourist_guide.activites.changeImage.ChangeImageActivity
import com.example.mobile_tourist_guide.activites.createProfile.CreateProfileActivity
import com.example.mobile_tourist_guide.activites.main.MainActivity
import com.example.mobile_tourist_guide.activites.register.RegisterActivity


abstract class BaseFragment: Fragment(){
    protected fun goToActivity(){
        val intent = Intent(requireContext(), MainActivity::class.java).apply{
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        startActivity(intent)
    }

    protected fun goToRegister(){
        val intent = Intent(requireContext(), RegisterActivity::class.java).apply{
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        startActivity(intent)
    }

    protected fun goToCreateProfile(){
        val intent = Intent(requireContext(), CreateProfileActivity::class.java).apply{
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        startActivity(intent)
    }

    protected fun goToChangeImage(){
        val intent = Intent(requireContext(), ChangeImageActivity::class.java).apply{
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        startActivity(intent)
    }
}