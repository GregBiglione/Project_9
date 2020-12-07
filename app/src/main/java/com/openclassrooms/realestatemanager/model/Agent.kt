package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Agent(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
        var agentPhoto: String,
        var firstName: String,
        var name: String,
        var phoneNumber: String,
        var email: String
)