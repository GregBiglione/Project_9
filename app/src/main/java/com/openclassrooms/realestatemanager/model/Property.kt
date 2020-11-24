package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Property(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var photo1: String?, var descriptionPhoto1: String?,
        var photo2: String?, var descriptionPhoto2: String?,
        var photo3: String?, var descriptionPhoto3: String?,
        var photo4: String?, var descriptionPhoto4: String?,
        var photo5: String?, var descriptionPhoto5: String?,
        var photo6: String?, var descriptionPhoto6: String?,
        var photo7: String?, var descriptionPhoto7: String?,
        var photo8: String?, var descriptionPhoto8: String?,
        var photo9: String?, var descriptionPhoto9: String?,
        var photo10: String?, var descriptionPhoto10: String?,
        var typeOfProperty: String?,
        var neighborhood: String?,
        var address: String?,
        var price: Int?,
        var surface: Int?,
        var numberOfRooms: Int?,
        var numberOfBathRooms: Int?,
        var numberOfBedRooms: Int?,
        var description: String?,
        var status: Boolean?,
        var available: String?,
        var proximityPointsOfInterest: List<String>?,
        var entryDate: Calendar?,
        var saleDate: Calendar?,
        var agentInCharge: String?
)