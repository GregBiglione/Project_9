package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Agent

interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createAgent(agent: Agent)

    @Query("SELECT * FROM Agent")
    fun getAllAgents(): LiveData<List<Agent>>

    @Update
    fun updateAgent(agent: Agent)

    @Query("DELETE FROM Agent WHERE id = id")
    fun deleteAgent(id: Int)
}