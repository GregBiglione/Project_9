package com.openclassrooms.realestatemanager.geocodinglocation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import java.io.IOException
import java.util.*

class GeoCodingLocation{

    private val TAG = "GeoCodingLocation"

    fun getAddressFromLocation(locationAddress: String, context: Context): String? {
        val geoCoder = Geocoder(context, Locale.getDefault())
        var latLng: String? = null
        try {
            val addressList = geoCoder.getFromLocationName(locationAddress, 1)
            if (addressList != null && addressList.size > 0){
                val address = addressList[0] as Address
                val lat = address.latitude.toString()
                val lng = address.longitude.toString()

                latLng = "$lat $lng"
            }
        } catch (e: IOException) {
            Log.e(TAG, "Unable to connect to GeoCoder", e)
        }
        return latLng
    }
}