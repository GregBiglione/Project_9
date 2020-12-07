package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.database.dao.AgentDao
import com.openclassrooms.realestatemanager.model.Agent

class AgentRepository(private val agentDao: AgentDao) {

    val getAllAgents = agentDao.getAllAgents()

    fun getAgent(id: Long){
        agentDao.getAgent(id)
    }

    fun createAgent(agent: Agent){
        agentDao.createAgent(agent)
    }

    fun updateAgent(agent: Agent){
        agentDao.updateAgent(agent)
    }

    fun deleteAgent(agent: Agent){
        agentDao.deleteAgent(agent)
    }
}

