package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Agent(
        var agentPhoto: String,
        var name: String,
        var firstName: String,
        var phoneNumber: String,
        var email: String
){
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L

}