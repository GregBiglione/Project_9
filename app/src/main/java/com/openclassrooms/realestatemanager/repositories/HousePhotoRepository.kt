package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.database.dao.HousePhotoDao
import com.openclassrooms.realestatemanager.model.HousePhoto

class HousePhotoRepository(private val housePhotoDao: HousePhotoDao) {

    val getAllHousePhotos = housePhotoDao.getAllHousePhotos()

    fun getHousePhoto(id: Long){
        housePhotoDao.getHousePhoto(id)
    }

    fun createHousePhoto(housePhoto: HousePhoto){
        housePhotoDao.createHousePhoto(housePhoto)
    }

    fun updateHousePhoto(housePhoto: HousePhoto){
        housePhotoDao.updateHousePhoto(housePhoto)
    }

    fun deleteHousePhoto(housePhoto: HousePhoto){
        housePhotoDao.deleteHousePhoto(housePhoto)
    }

}