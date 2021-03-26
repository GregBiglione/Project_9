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

    @Query("""SELECT * FROM house WHERE typeOfHouse = :type AND neighborhood = :neighborhood AND price BETWEEN :minPrice AND :maxPrice 
        AND surface BETWEEN :minSurface AND :maxSurface AND numberOfRooms BETWEEN :minRooms AND :maxRooms 
        AND numberOfBathRooms BETWEEN :minBathrooms AND :maxBathrooms OR numberOfBedRooms BETWEEN :minBedrooms AND :maxBedrooms 
        AND available = :status AND proximityPointsOfInterest = :poi AND entryDate = :entryDate 
        AND saleDate = :saleDate AND agentId = :agentId""")
    fun getAllHousesFiltered(type: String,
                             neighborhood: String,
                             minPrice: Int, maxPrice: Int,
                             minSurface: Int, maxSurface: Int,
                             minRooms: Int, maxRooms: Int,
                             minBathrooms: Int, maxBathrooms: Int,
                             minBedrooms: Int, maxBedrooms: Int,
                             status: String,
                             poi: String?,
                             entryDate: Long?, saleDate: Long?,
                             agentId: Long): LiveData<List<House>>

    //------------------- Cursor handled by content provider ---------------------------------------

    @Query("SELECT * FROM house WHERE id = :id")
    fun getHouseWithCursor(id: Long): Cursor

}