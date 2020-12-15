package com.openclassrooms.realestatemanager.model

import androidx.room.*
import com.openclassrooms.realestatemanager.utils.ListConverters

@Entity(foreignKeys = [ForeignKey(entity = Agent::class,
            parentColumns = ["id"],
            childColumns = ["agentId"])])
data class House (
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
        @TypeConverters(ListConverters::class)
        var housePhotoList: ArrayList<HousePhoto>?,
        var typeOfHouse: String?,
        var neighborhood: String?,
        var address: String?,
        var price: Long?,
        var surface: Int?,
        var numberOfRooms: Int?,
        var numberOfBathRooms: Int?,
        var numberOfBedRooms: Int?,
        var description: String?,
        var available: String?,
        var proximityPointsOfInterest: String?,
        var entryDate: Long?,
        var saleDate: Long?,
        @ColumnInfo(name = "agentId", index = true)
        var agentId: Long?
)