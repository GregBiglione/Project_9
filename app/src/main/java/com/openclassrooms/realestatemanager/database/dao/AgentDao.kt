package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Agent

@Dao
interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createAgent(agent: Agent)

    @Query("SELECT * FROM agent")
    fun getAllAgents(): LiveData<List<Agent>>

    @Query("SELECT * FROM agent WHERE id = :id")
    fun getAgent(id: Long): LiveData<List<Agent>>

    @Update
    fun updateAgent(agent: Agent)

    @Delete
    fun deleteAgent(agent: Agent)
}