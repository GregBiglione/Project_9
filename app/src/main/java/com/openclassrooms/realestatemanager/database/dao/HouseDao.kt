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