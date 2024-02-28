package com.example.mobile_tourist_guide.activites.main

import android.os.Bundle

import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.abstractClasses.BaseActivity
import com.example.mobile_tourist_guide.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Wyłączenie ciemnego trybu
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //Databinding i nawigacja
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //dodanie menagera fragmentów do aktywności
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //dodanie obsługi dolnego menu

        binding.bottomNavbar.setupWithNavController(navController)
        NavOptions.Builder()
            .setPopUpTo(R.id.mainFragment, true)
            .build()
        NavOptions.Builder()
            .setPopUpTo(R.id.mapFragment, true)
            .build()
        NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, true)
            .build()
        NavOptions.Builder()
            .setPopUpTo(R.id.settingsFragment, true)
            .build()

        val activity = this@MainActivity
        checkPermission(activity, activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE_PERMISSION_CODE)
    }

    override fun onStart() {
        super.onStart()
        val activity = this@MainActivity
        checkPermission(activity, activity, android.Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION_PERMISSION_CODE)
    }
}