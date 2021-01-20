package com.openclassrooms.realestatemanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Agent(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var agentPhoto: String?,
        var firstName: String,
        var name: String,
        var phoneNumber: String,
        var email: String
): Parcelable