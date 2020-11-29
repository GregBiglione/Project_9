package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Agent(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var agentPhoto: String,
        var name: String,
        var firstName: String,
        var phoneNumber: String,
        var email: String
)