package com.openclassrooms.realestatemanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(application: Application): AndroidViewModel(application) {

    //private val getAllProperties: LiveData<List<Property>>
    //private val propertyRepository: PropertyRepository
//
    //init{
    //    val propertyDao = RealEstateManagerDatabase.getDatabase(application).propertyDao()
    //    propertyRepository = PropertyRepository(propertyDao)
    //    getAllProperties = propertyRepository.getAllProperties
    //}
//
    //fun createProperty(property: Property){
    //    viewModelScope.launch(Dispatchers.IO) {
    //        propertyRepository.createProperty(property)
    //    }
    //}
//
    //fun updateProperty(property: Property){
    //    viewModelScope.launch(Dispatchers.IO) {
    //        propertyRepository.updateProperty(property)
    //    }
    //}
}