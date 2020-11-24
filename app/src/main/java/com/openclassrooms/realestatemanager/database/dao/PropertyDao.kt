package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createProperty(property: Property)

    @Query("SELECT * FROM Property")
    fun getAllProperties(): LiveData<List<Property>>

    @Update
    fun updateProperty(property: Property)
}