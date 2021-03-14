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

class MainViewModel(private val agentRepository: AgentRepository,
                    private val houseRepository: HouseRepository,
                    private val housePhotoRepository: HousePhotoRepository) : ViewModel() {

    val allAgents = agentRepository.getAllAgents
    val allHouses = houseRepository.getAllHouses
    val allHousePhotos = housePhotoRepository.getAllHousePhotos

    //------------------- Agents -------------------------------------------------------------------
    fun getAgent(id: Long) = viewModelScope.launch(Dispatchers.IO) { agentRepository.getAgent(id) }

    fun createAgent(agent: Agent) = viewModelScope.launch(Dispatchers.IO){ agentRepository.createAgent(agent)}

    fun updateAgent(agent: Agent) = viewModelScope.launch(Dispatchers.IO) { agentRepository.updateAgent(agent) }

    fun deleteAgent(agent: Agent) = viewModelScope.launch(Dispatchers.IO) { agentRepository.deleteAgent(agent) }

    //------------------- Houses -------------------------------------------------------------------
    fun getHouse(id: Long) = viewModelScope.launch(Dispatchers.IO) { houseRepository.getHouse(id) }

    fun createHouse(house: House) = viewModelScope.launch(Dispatchers.IO) { houseRepository.createHouse(house) }

    fun updateHouse(house: House) = viewModelScope.launch(Dispatchers.IO) {  houseRepository.updateHouse(house) }

    fun getAllHousesFiltered(minHousePhotoNumber: Int, maxHousePhotoNumber: Int,
                             type: String,
                             neighborhood: String,
                             minPrice: Int, maxPrice: Int,
                             minSurface: Int, maxSurface: Int,
                             minRooms: Int, maxRooms: Int,
                             minBathrooms: Int, maxBathrooms: Int,
                             minBedrooms: Int, maxBedrooms: Int,
                             status: String,
                             poi: String?,
                             entryDate: Long?, saleDate: Long?,
                             agentId: Long) =
            houseRepository.getAllHousesFiltered(minHousePhotoNumber, maxHousePhotoNumber, type, neighborhood, minPrice, maxPrice,
                    minSurface, maxSurface, minRooms, maxRooms, minBathrooms, maxBathrooms, minBedrooms, maxBedrooms, status,
                    poi, entryDate, saleDate, agentId)

    //------------------- HousePhotos --------------------------------------------------------------
    fun getHousePhoto(id: Long) = viewModelScope.launch(Dispatchers.IO) { housePhotoRepository.getHousePhoto(id) }

    fun createHousePhoto(housePhoto: HousePhoto) = viewModelScope.launch(Dispatchers.IO) { housePhotoRepository.createHousePhoto(housePhoto) }

    fun updateHousePhoto(housePhoto: HousePhoto) = viewModelScope.launch(Dispatchers.IO) { housePhotoRepository.updateHousePhoto(housePhoto) }

    fun deleteHousePhoto(housePhoto: HousePhoto) = viewModelScope.launch(Dispatchers.IO) { housePhotoRepository.deleteHousePhoto(housePhoto) }
}