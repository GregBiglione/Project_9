package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createProperty(property: Property)

    @Query("SELECT * FROM property")
    fun getAllProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM property WHERE id = :id")
    fun getProperty(id: Long): LiveData<List<Property>>

    @Update
    fun updateProperty(property: Property)
}