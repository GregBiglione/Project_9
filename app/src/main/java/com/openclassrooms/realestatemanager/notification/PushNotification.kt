package com.openclassrooms.realestatemanager.notification

data class PushNotification(
        val data: NotificationData,
        val to: String
)
