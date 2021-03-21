package com.openclassrooms.realestatemanager.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.droidman.ktoasty.showSuccessToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MapFragment: Fragment() {

    private var map: GoogleMap? = null
    companion object{
        const val DEFAULT_ZOOM = 17.0f
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val INTERNET = Manifest.permission.INTERNET
        const val LOCATION_PERMISSION_REQUEST_CODE = 21
    }
    //-------------------------------- Last known location -----------------------------------------
    private lateinit var gps: FloatingActionButton
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //-------------------------------- View Model --------------------------------------------------
    private lateinit var mainViewModel: MainViewModel
    private lateinit var injection: Injection


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        injection = Injection()
        configureViewModel()
        addHouseMarker()
        createLocationService()
        gps = view.findViewById(R.id.gps)
        customFocus()
        return view
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show map when ready & add a maker ---------------------------
    //----------------------------------------------------------------------------------------------

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        if (Utils.isInternetAvailableNew(requireContext())) {
            lastKnownLocation()
            checkPermissions()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Zoom level --------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun zoomOnLocation(){
        map!!.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM))
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Check permissions -------------------------------------------
    //----------------------------------------------------------------------------------------------

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(LOCATION_PERMISSION_REQUEST_CODE)
    private fun checkPermissions() {
        val perms = arrayOf(ACCESS_FINE_LOCATION, INTERNET)

        if (EasyPermissions.hasPermissions(requireContext(), *perms)){
            activity?.showSuccessToast(getString(R.string.location_granted), Toast.LENGTH_SHORT, true)
        }
        else{
            EasyPermissions.requestPermissions(this, getString(R.string.permissions_message),
                    LOCATION_PERMISSION_REQUEST_CODE, *perms)
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Center position on last known location ----------------------
    //----------------------------------------------------------------------------------------------

    private fun customFocus() {
        gps.setOnClickListener { lastKnownLocation() }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Get last known location -------------------------------------
    //----------------------------------------------------------------------------------------------

    //-------------------------------- Location service --------------------------------------------

    private fun createLocationService() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    @SuppressLint("MissingPermission")
    private fun lastKnownLocation(){
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null){
                val littleItaly = LatLng(location.latitude, location.longitude)
                map!!.addMarker(MarkerOptions().position(littleItaly))
                map!!.moveCamera(CameraUpdateFactory.newLatLng(littleItaly))
                zoomOnLocation()
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure ViewModel -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Add marker on map for each house ----------------------------
    //----------------------------------------------------------------------------------------------

    private fun addHouseMarker(){
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //------------------- Get houses from room db ----------------------------------------------
        mainViewModel.allHouses.observe(viewLifecycleOwner, { house ->
            for (h in house) {
                val houseLat = h.lat
                val houseLng = h.lng
                val houseLocation = LatLng(houseLat!!, houseLng!!)

                when(h.available){
                    "Available" -> {
                        if (houseLat != 0.0 && houseLng != 0.0){
                            val availableMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker_available)
                            map!!.addMarker(MarkerOptions()
                                    .position(houseLocation)
                                    .icon(availableMarker)
                                    .title(h.address))
                        }
                    }
                    "Sold" ->  if (houseLat != 0.0 && houseLng != 0.0){
                        val soldMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker_sold)
                        map!!.addMarker(MarkerOptions()
                                .position(houseLocation)
                                .icon(soldMarker)
                                .title(h.address))
                        clickOnMarker()
                    }
                }
            }
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Click on marker ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clickOnMarker(){
        map?.setOnMarkerClickListener(OnMarkerClickListener { marker ->
            mainViewModel.allHouses.observe(viewLifecycleOwner, { house ->
                for (h in house){
                    val markerTitle = marker.title
                    if (h.address.equals(markerTitle)){
                        val action = MapFragmentDirections.actionNavMapToDetailedHouseFragment(h)
                        findNavController().navigate(action)
                    }
                }
            })
            false
        })
    }
}