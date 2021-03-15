package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.HouseDao
import com.openclassrooms.realestatemanager.model.House

class HouseRepository(private val houseDao: HouseDao) {

    val getAllHouses = houseDao.getAllHouses()

    fun getHouse(id: Long){
        houseDao.getHouse(id)
    }

    fun createHouse(house: House){
        houseDao.createHouse(house)
    }

    fun updateHouse(house: House){
        houseDao.updateHouse(house)
    }

    fun getAllHousesFiltered(/*minHousePhotoNumber: Int, maxHousePhotoNumber: Int,*/
                             type: String/*,
                             neighborhood: String,
                             minPrice: Int, maxPrice: Int,
                             minSurface: Int, maxSurface: Int,
                             minRooms: Int, maxRooms: Int,
                             minBathrooms: Int, maxBathrooms: Int,
                             minBedrooms: Int, maxBedrooms: Int,
                             status: String,
                             poi: String?,
                             entryDate: Long?, saleDate: Long?,
                             agentId: Long*/): LiveData<List<House>> {
        return houseDao.getAllHousesFiltered(/*minHousePhotoNumber, maxHousePhotoNumber, */type/*, neighborhood, minPrice, maxPrice,
                minSurface, maxSurface, minRooms, maxRooms, minBathrooms, maxBathrooms, minBedrooms, maxBedrooms, status,
                poi, entryDate, saleDate, agentId*/)
    }
}