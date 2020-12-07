package com.openclassrooms.realestatemanager.repositories

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
}