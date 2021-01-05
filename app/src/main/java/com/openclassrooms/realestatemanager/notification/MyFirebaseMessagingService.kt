package com.openclassrooms.realestatemanager.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.activities.MainActivity

class MyFirebaseMessagingService: FirebaseMessagingService() {

    val TAG = "NotificationService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: " + remoteMessage.from)
        Log.d(TAG, "Notification message body: " + (remoteMessage.notification?.body))
    }
    //val TAG = "NotificationService"
//
    //override fun onMessageReceived(remoteMessage: RemoteMessage) {
    //    Log.e(TAG, "Notification log: ${remoteMessage.from}")
//
    //    if (remoteMessage.notification != null){
    //        showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
    //    }
    //}
//
    //private fun showNotification(title: String?, body: String?) {
//
    //    //----------------------------- Create an Intent that will be shown when user will click on the Notification ------
    //    val intent = Intent(this, MainActivity::class.java)
    //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    //    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//
    //    //----------------------------- Build a Notification object --------------------------------
    //    val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    //    val notificationBuilder = NotificationCompat.Builder(this)
    //            .setSmallIcon(R.drawable.ic_baseline_hot_tub_24)
    //            .setContentTitle(title)
    //            .setContentText(body)
    //            .setAutoCancel(true)
    //            .setSound(soundUri)
    //            .setContentIntent(pendingIntent)
//
    //    //----------------------------- Show notification ------------------------------------------
    //    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    //    notificationManager.notify(0, notificationBuilder.build())
    //}
}