package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.launch

class MainViewModel(private val agentRepository: AgentRepository, private val propertyRepository: PropertyRepository) : ViewModel() {

    val agents = agentRepository.getAllAgents
    val properties = propertyRepository.getAllProperties

    //------------------- Agents -------------------------------------------------------------------
    fun getAgent(id: Int) = viewModelScope.launch { agentRepository.getAgent(id) }

    fun createAgent(agent: Agent) = viewModelScope.launch { agentRepository.createAgent(agent) }

    fun updateAgent(agent: Agent) = viewModelScope.launch { agentRepository.updateAgent(agent) }

    fun deleteAgent(agent: Agent) = viewModelScope.launch { agentRepository.deleteAgent(agent) }

    //------------------- Properties ---------------------------------------------------------------
    fun getProperty(id: Int) = viewModelScope.launch { propertyRepository.getProperty(id) }

    fun createProperty(property: Property) = viewModelScope.launch { propertyRepository.createProperty(property) }

    fun updateProperty(property: Property) = viewModelScope.launch {  propertyRepository.updateProperty(property)  }
}