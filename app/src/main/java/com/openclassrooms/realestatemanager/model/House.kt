package com.openclassrooms.realestatemanager.model

import android.content.ClipData.Item
import android.content.ContentValues
import android.os.Parcelable
import androidx.room.*
import com.openclassrooms.realestatemanager.utils.ListConverters
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(foreignKeys = [ForeignKey(entity = Agent::class,
        parentColumns = ["id"],
        childColumns = ["agentId"])])
data class House(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
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
        var entryDate: Long,
        var saleDate: Long?,
        @ColumnInfo(name = "agentId", index = true)
        var agentId: Long?
): Parcelable
//{
//
//        //-------------------------------- Utils ---------------------------------------------------
//
//        fun fromContentValues(values: ContentValues): House {
//                val house = House(id, housePhotoList, typeOfHouse, neighborhood, address, price, surface, numberOfRooms, numberOfBathRooms,
//                        numberOfBedRooms, description, available, proximityPointsOfInterest, entryDate, saleDate, agentId)
//                if (values.containsKey("housePhotoList")) house.housePhotoList
//                if (values.containsKey("typeOfHouse")) house.typeOfHouse
//                if (values.containsKey("neighborhood")) house.neighborhood
//                if (values.containsKey("address")) house.address
//                if (values.containsKey("price")) house.price
//                if (values.containsKey("surface")) house.surface
//                if (values.containsKey("numberOfRooms")) house.numberOfRooms
//                if (values.containsKey("numberOfBathRooms")) house.numberOfBathRooms
//                if (values.containsKey("numberOfBedRooms")) house.numberOfBedRooms
//                if (values.containsKey("description")) house.description
//                if (values.containsKey("available")) house.available
//                if (values.containsKey("proximityPointsOfInterest")) house.proximityPointsOfInterest
//                if (values.containsKey("entryDate")) house.entryDate
//                if (values.containsKey("saleDate")) house.saleDate
//                if (values.containsKey("agentId")) house.agentId
//                return house
//        }
//}

