package com.openclassrooms.realestatemanager.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.utils.ListConverters
import com.openclassrooms.realestatemanager.utils.UriConverters
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
@TypeConverters(ListConverters::class)
data class HousePhoto (
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var photo: String?,
        var photoDescription: String
): Parcelable