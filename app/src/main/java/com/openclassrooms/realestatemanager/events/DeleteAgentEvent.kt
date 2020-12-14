package com.openclassrooms.realestatemanager.events

import com.openclassrooms.realestatemanager.model.Agent

data class DeleteAgentEvent(val agent: Agent) { }