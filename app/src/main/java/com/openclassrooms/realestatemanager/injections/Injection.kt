package com.openclassrooms.realestatemanager.injections

import android.content.Context
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository

class Injection {

    private fun provideAgentRepository(context: Context): AgentRepository {
        val realEstateManagerDatabase: RealEstateManagerDatabase = RealEstateManagerDatabase.getInstance(context)
        return AgentRepository(realEstateManagerDatabase.agentDao)
    }

    private fun provideHouseRepository(context: Context): HouseRepository {
        val realEstateManagerDatabase: RealEstateManagerDatabase = RealEstateManagerDatabase.getInstance(context)
        return HouseRepository(realEstateManagerDatabase.houseDao)
    }

    private fun provideHousePhotoRepository(context: Context): HousePhotoRepository {
        val realEstateManagerDatabase: RealEstateManagerDatabase = RealEstateManagerDatabase.getInstance(context)
        return HousePhotoRepository(realEstateManagerDatabase.housePhotoDao)
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val agentRepository: AgentRepository = provideAgentRepository(context)
        val houseRepository: HouseRepository = provideHouseRepository(context)
        val housePhotoRepository: HousePhotoRepository = provideHousePhotoRepository(context)
        return ViewModelFactory(agentRepository, houseRepository, housePhotoRepository)
    }
}