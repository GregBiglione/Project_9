package com.openclassrooms.realestatemanager.database.dao

import android.content.Context
import android.util.Property
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Agent

@Database(entities = [Property::class, Agent::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    //------------------- DAO ----------------------------------------------------------------------
    abstract val agentDao: AgentDao
    abstract val propertyDao: PropertyDao

    //------------------- Singleton ----------------------------------------------------------------
    companion object{
        @Volatile
        private var INSTANCE: RealEstateManagerDatabase? = null
        fun getInstance(context: Context): RealEstateManagerDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            RealEstateManagerDatabase::class.java,
                            "RealEstateManager"
                    )
                            .build()
                }
                return instance
            }
        }
    }
}