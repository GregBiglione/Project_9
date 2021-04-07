package com.openclassrooms.realestatemanager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.Constants
import com.openclassrooms.realestatemanager.utils.Constants.Companion.NOTIFICATION_CHANNEL_ID
import com.openclassrooms.realestatemanager.utils.Constants.Companion.NOTIFICATION_CHANNEL_IMPORTANCE
import com.openclassrooms.realestatemanager.utils.Constants.Companion.NOTIFICATION_CHANNEL_NAME
import com.openclassrooms.realestatemanager.utils.Constants.Companion.NOTIFICATION_RINGTONE_MANAGER

class Notification {

    fun sendNotification(context: Context){

        val sharePreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val receivedNotification = sharePreferences.getBoolean("switchNotification", true)

        if (receivedNotification){
            //----------------------------- Build a Notification object --------------------------------
            val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.real_estate_manager_logo)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(context.getString(R.string.notification_message))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(NOTIFICATION_RINGTONE_MANAGER))

            //----------------------------- Add the Notification to the Notification Manager and show it -------------
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            //----------------------------- Support Version >= Android 8 -------------------------------
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NOTIFICATION_CHANNEL_IMPORTANCE)
                notificationManager.createNotificationChannel(channel)
            }

            //----------------------------- Show notification ------------------------------------------
            notificationManager.notify(Constants.NOTIFICATION_TAG, Constants.NOTIFICATION_ID, notificationBuilder.build())
        }
    }
}