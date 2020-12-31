package com.openclassrooms.realestatemanager.model

import android.os.Parcelable
import androidx.room.*
import com.openclassrooms.realestatemanager.utils.ListConverters
import kotlinx.android.parcel.Parcelize

@Parcelize
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
        var price: Int?,
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
): Parcelable