package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R


class MapFragment: Fragment() {

    private var map: GoogleMap? = null
    private val DEFAULT_ZOOM = 17.0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Show map when ready & add a maker ----------------------------------------
    //----------------------------------------------------------------------------------------------

    private val callback = OnMapReadyCallback { googleMap ->
        val milan = LatLng(45.4637, 9.1905)
        map = googleMap
        googleMap.addMarker(MarkerOptions().position(milan).title("Marker in Milan"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(milan))
        zoomOnLocation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Zoom level ---------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun zoomOnLocation(){
        map!!.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM))
    }
}