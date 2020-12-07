package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Agent::class,
            parentColumns = ["id"],
            childColumns = ["agentId"])])
class House (
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
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
        var typeOfHouse: String?,
        var neighborhood: String?,
        var address: String?,
        var price: Long?,
        var surface: Int?,
        var numberOfRooms: Int?,
        var numberOfBathRooms: Int?,
        var numberOfBedRooms: Int?,
        var description: String?,
        var status: Boolean,
        var available: String?,
        var proximityPointsOfInterest1: String?,
        var proximityPointsOfInterest2: String?,
        var proximityPointsOfInterest3: String?,
        var proximityPointsOfInterest4: String?,
        var proximityPointsOfInterest5: String?,
        var entryDate: Long?,
        var saleDate: Long?,
        var agentId: Long?

)