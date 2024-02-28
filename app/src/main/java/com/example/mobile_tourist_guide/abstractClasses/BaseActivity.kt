package com.example.mobile_tourist_guide.abstractClasses

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

abstract class BaseActivity:AppCompatActivity() {

    companion object{
        const val STORAGE_PERMISSION_CODE = 101
        const val ACCESS_FINE_LOCATION_PERMISSION_CODE = 105
        const val WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 110
    }

    protected fun checkPermission(activity: Activity,context: Context, permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}