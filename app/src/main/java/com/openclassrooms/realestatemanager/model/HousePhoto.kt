package com.openclassrooms.realestatemanager.model

import android.net.Uri
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.utils.ListConverters
import com.openclassrooms.realestatemanager.utils.UriConverters

@TypeConverters(ListConverters::class)
data class HousePhoto (
        var id: Long,
        @TypeConverters(UriConverters::class)
        var photo: Uri?,
        var photoDescription: String?
)