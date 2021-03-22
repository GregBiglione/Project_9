package com.openclassrooms.realestatemanager.utils

import android.Manifest
import com.openclassrooms.realestatemanager.BuildConfig

class Constants {

    companion object{
        //----------------------------- Notification -----------------------------------------------
        const val BASE_URL = "https://fcm.googleapis.com"
        const val SERVER_KEY = BuildConfig.ServerKey
        const val CONTENT_TYPE = "application/json"

        const val TOPIC = "/topic/myTopic"
        const val NOTIFICATION_ID = 2109
        const val NOTIFICATION_TAG = "FIRE_BASE_REAL_ESTATE_MANGER"

        //----------------------------- Camera & gallery -------------------------------------------
        const val CAMERA = Manifest.permission.CAMERA
        const val READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        const val WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val GALLERY_REQUEST_CODE = 1201
        const val CAMERA_REQUEST_CODE = 807
        const val IMAGE_PICK_CODE = 2108
        const val TAKE_PHOTO_CODE = 3003

        //----------------------------- Map --------------------------------------------------------
        const val DEFAULT_ZOOM = 17.0f
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val INTERNET = Manifest.permission.INTERNET
        const val LOCATION_PERMISSION_REQUEST_CODE = 21

        //----------------------------- Settings ---------------------------------------------------
        const val NOTIFICATIONS_PREF = "Notifications preferences"
    }
}