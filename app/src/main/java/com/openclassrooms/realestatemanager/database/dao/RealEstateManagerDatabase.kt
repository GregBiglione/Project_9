package com.openclassrooms.realestatemanager.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.model.HousePhoto
import com.openclassrooms.realestatemanager.utils.ListConverters
import com.openclassrooms.realestatemanager.utils.UriConverters

@Database(entities = [House::class, Agent::class, HousePhoto::class], version = 1, exportSchema = false)
@TypeConverters(UriConverters::class, ListConverters::class)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    //------------------- DAO ----------------------------------------------------------------------
    abstract val agentDao: AgentDao
    abstract val houseDao: HouseDao
    abstract val housePhotoDao: HousePhotoDao

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