package com.openclassrooms.realestatemanager.utils

import android.Manifest
import android.app.NotificationManager
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.openclassrooms.realestatemanager.BuildConfig

class Constants {

    companion object{
        //----------------------------- Api key ----------------------------------------------------
        const val API_KEY = BuildConfig.ApiKey
        //----------------------------- Notification -----------------------------------------------
        const val NOTIFICATION_CHANNEL_ID = "notification channel"
        const val NOTIFICATION_CHANNEL_NAME = "Message from Real Estate Manager"
        @RequiresApi(Build.VERSION_CODES.N)
        const val NOTIFICATION_CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        const val NOTIFICATION_RINGTONE_MANAGER = RingtoneManager.TYPE_NOTIFICATION
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