package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.House

@Dao
interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createHouse(house: House)

    @Query("SELECT * FROM house")
    fun getAllHouses(): LiveData<List<House>>

    @Query("SELECT * FROM house WHERE id = :id")
    fun getHouse(id: Long): LiveData<List<House>>

    @Update
    fun updateHouse(house: House)
}