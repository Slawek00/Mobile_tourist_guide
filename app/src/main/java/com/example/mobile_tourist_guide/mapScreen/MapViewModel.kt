package com.example.mobile_tourist_guide.mapScreen


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.data.RoutesPoints
import org.osmdroid.util.GeoPoint


class MapViewModel: ViewModel() {
    val geoPointList = MutableLiveData<ArrayList<GeoPoint>>()
    fun getPoints(list: RoutesPoints):ArrayList<GeoPoint>{
        var array = ArrayList<GeoPoint>()
        for(element in list.points!!){
            val tmp = element!!.split(",")
            val geoPoint = GeoPoint(tmp[0].toDouble(),tmp[1].toDouble())
            array.add(geoPoint)
        }
        return array
    }
}