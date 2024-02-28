package com.example.mobile_tourist_guide.mapScreen

import android.app.AlertDialog
import android.graphics.Rect
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.data.RoutesPoints
import com.example.mobile_tourist_guide.databinding.FragmentMapBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.osmdroid.api.IMapController
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer
import org.osmdroid.bonuspack.location.NominatimPOIProvider
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.bonuspack.utils.BonusPackHelper
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment()  {

    private lateinit var binding: FragmentMapBinding
    private lateinit var map: MapView
    private lateinit var controller: IMapController
    private lateinit var compass: CompassOverlay
    private lateinit var provider: GpsMyLocationProvider
    private lateinit var myLocation: MyLocationNewOverlay
    private lateinit var addFAB: FloatingActionButton
    private lateinit var searchFAB: FloatingActionButton
    private lateinit var freeFAB: FloatingActionButton
    private lateinit var endButton: FloatingActionButton
    private var fabVisible = false
    private var searchText: String? = null
    private val viewModel: MapViewModel by viewModels()
    private lateinit var points: RoutesPoints

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map, container, false
        )

        Configuration.getInstance().load(
            requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

        addFAB = binding.addButton
        searchFAB = binding.searchButton
        freeFAB = binding.freeMode
        endButton = binding.endButton

        addFAB.setOnClickListener{
            if (!fabVisible) {
                freeFAB.show()
                searchFAB.show()
                endButton.show()
                freeFAB.visibility = View.VISIBLE
                searchFAB.visibility = View.VISIBLE
                endButton.visibility = View.VISIBLE

                addFAB.setImageDrawable(resources.getDrawable(R.drawable.baseline_close_24))
                fabVisible = true
            } else {
                freeFAB.hide()
                searchFAB.hide()
                endButton.hide()
                freeFAB.visibility = View.GONE
                searchFAB.visibility = View.GONE
                endButton.visibility = View.GONE

                addFAB.setImageDrawable(resources.getDrawable(R.drawable.add_map))
                fabVisible = false
            }
        }

        searchFAB.setOnClickListener {
            search()
        }

        freeFAB.setOnClickListener {

        }

        endButton.setOnClickListener{
            stop()
        }

        map = binding.mapView
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.mapCenter
        map.setMultiTouchControls(true)
        map.getLocalVisibleRect(Rect())

        controller = map.controller
        controller.setZoom(14.0)
        controller.setCenter(SZCZECIN_GEO_POINT)

        compass = CompassOverlay(requireContext(), InternalCompassOrientationProvider(requireContext()), map)
        compass.enableCompass()

        provider = GpsMyLocationProvider(requireContext())
        provider.addLocationSource(LocationManager.NETWORK_PROVIDER)
        myLocation = MyLocationNewOverlay(provider, map)
        myLocation.enableMyLocation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataBundle = arguments
        val dataJson = dataBundle?.getString("points")
        if(dataJson != null){
            points = Json.decodeFromString<RoutesPoints>(dataJson)
            val array = viewModel.getPoints(points)
            viewModel.geoPointList.postValue(array)
        }
        viewModel.geoPointList.observe(viewLifecycleOwner){
            startPrefRoute(it)
        }
        map.invalidate()
        map.overlayManager.clear()
        map.overlays.add(compass)
        map.overlays.add(myLocation)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    private fun search(){
        val alertDialog = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_search, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.searchText)

        with(alertDialog){
            setTitle("Wyszukaj ")
            setPositiveButton("Szukaj"){ _, _ ->
                searchText = editText.text.toString()
                searchPOI()
            }
            setNegativeButton("Anuluj"){ _, _ ->

            }
            setView(dialogLayout)
            show()
        }
    }

    private fun stop(){
        map.overlays.clear()
        map.invalidate()
        viewModel.geoPointList.value?.clear()
        map.overlays.add(compass)
        map.overlays.add(myLocation)
    }

    private fun searchPOI(){
        clearOverlays()
        CoroutineScope(Dispatchers.IO).launch{
            if(searchText != null){
                val poiSearchProvider = NominatimPOIProvider(requireContext().packageName)
                val pois = poiSearchProvider.getPOICloseTo(SZCZECIN_GEO_POINT, searchText, 100, 0.1)
                val poiMarkers = RadiusMarkerClusterer(requireContext())
                val clusterIcon = BonusPackHelper.getBitmapFromVectorDrawable(requireContext(), R.drawable.marker_cluster)
                poiMarkers.setIcon(clusterIcon)
                map.overlays.add(poiMarkers)
                for (poi in pois) {
                    val poiMarker = Marker(map)
                    poiMarker.title = poi.mType
                    poiMarker.snippet = poi.mDescription
                    poiMarker.position = poi.mLocation
                    poiMarkers.add(poiMarker)
                    poiMarker.relatedObject = poi
                }
            }
            searchText = null
        }
        CoroutineScope(Dispatchers.IO).cancel()
    }

    private fun startPrefRoute(listPoint: ArrayList<GeoPoint>){
        clearOverlays()
        CoroutineScope(Dispatchers.IO).launch {
            val roadManager: RoadManager = OSRMRoadManager(requireContext(), requireContext().packageName)
            (roadManager as OSRMRoadManager).setMean(OSRMRoadManager.MEAN_BY_FOOT)
            val road = roadManager.getRoad(listPoint)
            val roadOverlay = RoadManager.buildRoadOverlay(road)
            map.overlays.add(roadOverlay)
            for (poi in listPoint) {
                val poiMarker = Marker(map)
                poiMarker.position = poi
                poiMarker.subDescription = "Kolejny krok trasy"
                poiMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                map.overlays.add(poiMarker)
            }
        }
        CoroutineScope(Dispatchers.IO).cancel()
    }

    private fun clearOverlays(){
        if(map.overlays.size != 2){
            map.overlays.clear()
        }
    }

    companion object{
        private val SZCZECIN_GEO_POINT = GeoPoint(53.4481,14.5372)
    }
}