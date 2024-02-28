package com.example.mobile_tourist_guide.activites.register


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.abstractClasses.BaseActivity
import com.example.mobile_tourist_guide.activites.main.MainActivity
import com.example.mobile_tourist_guide.databinding.ActivityRegisterBinding
import com.example.mobile_tourist_guide.network.FirebaseAPI

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Wyłączenie ciemnego trybu
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //Databinding i nawigacja
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        //dodanie menagera fragmentów do aktywności
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_register) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onStart() {
        super.onStart()
        isCurrentUser()
        val activity = this@RegisterActivity
        checkPermission(activity, activity, android.Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
    }

   private fun isCurrentUser(){
        FirebaseAPI.authFirebase.currentUser?.let {_ ->
            val intent = Intent(applicationContext, MainActivity::class.java).apply{
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
   }
}
