package com.openclassrooms.realestatemanager.model

import java.util.*

class Property(
        var id: Int,
        var photo1: String?,
        var photo2: String?,
        var photo3: String?,
        var photo4: String?,
        var photo5: String?,
        var photo6: String?,
        var photo7: String?,
        var photo8: String?,
        var photo9: String?,
        var photo10: String?,
        var typeOfProperty: String?,
        var neighborhood: String?,
        var address: String?,
        var price: Int?,
        var surface: Int?,
        var numberOfRooms: Int?,
        var numberOfBathRooms: Int?,
        var numberOfBedRooms: Int?,
        var description: Int?,
        var status: Boolean?,
        var proximityPointsOfInterest: String?,
        var entryDate: Date?,
        var saleDate: Date?,
        var agentInCharge: String?
)