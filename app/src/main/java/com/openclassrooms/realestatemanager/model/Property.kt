package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Agent::class,
                        parentColumns = ["id"],
                        childColumns = ["agentId"])])
data class Property(
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
){
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L
        //constructor(photo1: String?, descriptionPhoto1: String?, photo2: String?, descriptionPhoto2: String?, photo3: String?, descriptionPhoto3: String?,
        //            photo4: String?, descriptionPhoto4: String?, photo5: String?, descriptionPhoto5: String?, photo6: String?, descriptionPhoto6: String?,
        //            photo7: String?, descriptionPhoto7: String?, photo8: String?, descriptionPhoto8: String?, photo9: String?, descriptionPhoto9: String?,
        //            photo10: String?, descriptionPhoto10: String?, typeOfProperty: String?, neighborhood: String?, address: String?, price: Long?, surface: Int?,
        //            numberOfRooms: Int?, numberOfBathRooms: Int?, numberOfBedRooms: Int?, description: String?, status: Boolean, available: String?,
        //            proximityPointsOfInterest1: String?, proximityPointsOfInterest2: String?, proximityPointsOfInterest3: String?,
        //            proximityPointsOfInterest4: String?, proximityPointsOfInterest5: String?, entryDate: Long?, saleDate: Long?, agentId: Long?)
        //constructor(): this( null, null, null, null, null, null, null,
        //        null, null, null, null, null, null, null, null,
        //        null, null, null, null, null, null, null,
        //        null, null, null, null, null, null, null, true,
        //        null, null, null, null, null,
        //        null, null, null, null)
}