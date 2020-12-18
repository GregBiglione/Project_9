package com.openclassrooms.realestatemanager.events

import com.openclassrooms.realestatemanager.model.HousePhoto

data class DeleteHousePhotoEvent(val housePhoto: HousePhoto){}
