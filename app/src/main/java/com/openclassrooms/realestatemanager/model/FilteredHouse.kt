package com.openclassrooms.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable

data class FilteredHouse(
        var minPhotos: Int,
        var maxPhotos: Int,
        var type: String,
        var neighborhood: String,
        var minPrice: Int,
        val maxPrice: Int,
        val minSurface: Int,
        val maxSurface: Int,
        val minRooms: Int,
        val maxRooms: Int,
        val minBathrooms: Int,
        val maxBathrooms: Int,
        val minBedrooms: Int,
        val maxBedrooms: Int,
        var status: String,
        var poi: String?,
        var entryDate: Long?,
        var saleDate: Long?,
        var agentId: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(minPhotos)
        parcel.writeInt(maxPhotos)
        parcel.writeString(type)
        parcel.writeString(neighborhood)
        parcel.writeInt(minPrice)
        parcel.writeInt(maxPrice)
        parcel.writeInt(minSurface)
        parcel.writeInt(maxSurface)
        parcel.writeInt(minRooms)
        parcel.writeInt(maxRooms)
        parcel.writeInt(minBathrooms)
        parcel.writeInt(maxBathrooms)
        parcel.writeInt(minBedrooms)
        parcel.writeInt(maxBedrooms)
        parcel.writeString(status)
        parcel.writeString(poi)
        parcel.writeValue(entryDate)
        parcel.writeValue(saleDate)
        parcel.writeLong(agentId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilteredHouse> {
        override fun createFromParcel(parcel: Parcel): FilteredHouse {
            return FilteredHouse(parcel)
        }

        override fun newArray(size: Int): Array<FilteredHouse?> {
            return arrayOfNulls(size)
        }
    }
}
