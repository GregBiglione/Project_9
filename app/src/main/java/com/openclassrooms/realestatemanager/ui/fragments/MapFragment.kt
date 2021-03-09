package com.openclassrooms.realestatemanager.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.droidman.ktoasty.showSuccessToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show map when ready & add a maker ---------------------------
    //----------------------------------------------------------------------------------------------

    private val callback = OnMapReadyCallback { googleMap ->
        val milan = LatLng(45.4637, 9.1905)
        map = googleMap
        googleMap.addMarker(MarkerOptions().position(milan).title("Marker in Milan"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(milan))
        zoomOnLocation()
        checkPermissions()
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
            activity?.showSuccessToast("Location granted", Toast.LENGTH_SHORT, true)
        }
        else{
            EasyPermissions.requestPermissions(this,getString(R.string.permissions_message),
                    LOCATION_PERMISSION_REQUEST_CODE, *perms);
        }
    }
}