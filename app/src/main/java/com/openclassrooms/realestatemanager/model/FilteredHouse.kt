package com.openclassrooms.realestatemanager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilteredHouse(
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
) : Parcelable
