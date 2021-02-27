package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.model.HousePhoto
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterMainViewModel(private val agentRepository: AgentRepository,
                          private val houseRepository: HouseRepository,
                          private val housePhotoRepository: HousePhotoRepository) : ViewModel() {

    fun getAllHousesFiltered(agentId: Long) = viewModelScope.launch(Dispatchers.IO) { houseRepository.getAllHousesFiltered(agentId) }

}