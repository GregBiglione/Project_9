package com.openclassrooms.realestatemanager.repository


import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.PropertyDao
import com.openclassrooms.realestatemanager.model.Property

class PropertyRepository(private val propertyDao: PropertyDao) {

    val getAllProperties: LiveData<List<Property>> = propertyDao.getAllProperties()

    fun createProperty(property: Property){
        propertyDao.createProperty(property)
    }

    fun updateProperty(property: Property){
        propertyDao.updateProperty(property)
    }
}