package com.openclassrooms.realestatemanager.geocodinglocation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Message
import android.util.Log
import java.io.IOException
import java.util.*

class GeoCodingLocation {

    private val TAG = "GeoCodingLocation"

    fun getAddressFromLocation(locationAddress: String, context: Context, handler: android.os.Handler){
        val thread = object : Thread(){
            override fun run() {
                val geoCoder = Geocoder(context, Locale.getDefault())
                var result: String? = null

                try {
                    val addressList = geoCoder.getFromLocationName(locationAddress, 1)
                    if (addressList != null && addressList.size > 0){
                        val address = addressList[0] as Address
                        val sb = StringBuilder()
                        sb.append(address.latitude).toString()
                        sb.append(address.longitude).toString()
                        result = sb.toString()
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Unable to connect to GeoCoder", e)
                } finally {
                    val message = Message.obtain()
                    message.target = handler
                    message.what = 1
                    val bundle = Bundle()
                    bundle.putString("coordinates", result)
                    message.data = bundle
                    message.sendToTarget()
                }
            }
        }
        thread.start()
    }
}