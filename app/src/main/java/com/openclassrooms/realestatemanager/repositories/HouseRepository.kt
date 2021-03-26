package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.HouseDao
import com.openclassrooms.realestatemanager.model.FilteredHouse
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

    fun getAllHousesFiltered(filteredHouse: FilteredHouse): LiveData<List<House>> {
        return houseDao.getAllHousesFiltered(filteredHouse.type, filteredHouse.neighborhood, filteredHouse.minPrice, filteredHouse.maxPrice,
                filteredHouse.minSurface, filteredHouse.maxSurface, filteredHouse.minRooms, filteredHouse.maxRooms, filteredHouse.minBathrooms,
                filteredHouse.maxBathrooms, filteredHouse.minBedrooms, filteredHouse.maxBedrooms, filteredHouse.status, filteredHouse.poi,
                filteredHouse.entryDate, filteredHouse.saleDate, filteredHouse.agentId)
    }
}