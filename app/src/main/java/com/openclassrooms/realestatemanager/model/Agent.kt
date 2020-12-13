package com.openclassrooms.realestatemanager.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.utils.UriConverters
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Agent(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
        @TypeConverters(UriConverters::class)
        var agentPhoto: Uri,
        var firstName: String,
        var name: String,
        var phoneNumber: String,
        var email: String
): Parcelable