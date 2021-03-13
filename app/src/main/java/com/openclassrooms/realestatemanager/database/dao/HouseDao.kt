package com.openclassrooms.realestatemanager.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.House

@Dao
interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createHouse(house: House)

    @Query("SELECT * FROM house")
    fun getAllHouses(): LiveData<List<House>>

    @Query("SELECT * FROM house WHERE id = :id")
    fun getHouse(id: Long): LiveData<List<House>>

    @Update
    fun updateHouse(house: House)

    @Query("SELECT * FROM house WHERE /*housePhotoList BETWEEN :minHousePhotoNumber AND :maxHousePhotoNumber*/ /*OR*/ typeOfHouse = :type /*OR neighborhood = :neighborhood OR price BETWEEN :minPrice AND :maxPrice OR surface BETWEEN :minSurface AND :maxSurface OR numberOfRooms BETWEEN :minRooms AND :maxRooms OR numberOfBathRooms BETWEEN :minBathrooms AND :maxBathrooms OR numberOfBedRooms BETWEEN :minBedrooms AND :maxBedrooms OR available = :status OR proximityPointsOfInterest = :poi OR entryDate = :entryDate OR saleDate = :saleDate OR agentId = :agentId*/")
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
                             agentId: Long*/): LiveData<List<House>>

    //------------------- Cursor handled by content provider ---------------------------------------

    //@Query("SELECT * FROM house")
    //fun getAllHousesWithCursor(): Cursor?
//
    //@Query("SELECT * FROM house WHERE id = :id")
    //fun getHouseWithCursor(id: Long): Cursor?
//
    //@Update
    //fun updateHouseWithCursor(house: House): Cursor?
}