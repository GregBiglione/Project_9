package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.HousePhoto

@Dao
interface HousePhotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createHousePhoto(housePhoto: HousePhoto)

    @Query("SELECT * from housePhoto")
    fun getAllHousePhotos(): LiveData<List<HousePhoto>>

    @Query("SELECT * from housePhoto WHERE id = :id ")
    fun getHousePhoto(id: Long): LiveData<List<HousePhoto>>

    @Update
    fun updateHousePhoto(housePhoto: HousePhoto)

    @Delete
    fun deleteHousePhoto(housePhoto: HousePhoto)
}