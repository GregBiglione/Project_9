package com.openclassrooms.realestatemanager.model

data class Agent(
        var id: Int,
        var agentPhoto: String,
        var name: String,
        var firstName: String,
        var phoneNumber: String,
        var email: String
)