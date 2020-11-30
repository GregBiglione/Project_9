package com.openclassrooms.realestatemanager.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.AgentDataSource
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyDataSource

@Database(entities = [PropertyDataSource::class, AgentDataSource::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase: RoomDatabase() {

    //------------------- DAO ----------------------------------------------------------------------
    abstract val propertyDao: PropertyDao
    abstract val agentDao: AgentDao

    //------------------- Singleton ----------------------------------------------------------------
    companion object{
        @Volatile
        private var INSTANCE : RealEstateManagerDatabase? = null
        fun getInstance(context: Context): RealEstateManagerDatabase{
            synchronized(this){
                var  instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            RealEstateManagerDatabase::class.java,
                            "RealEstateManager_database"
                    )
                            .addCallback(prepopulate(context))
                            .build()
                }
                return instance
            }
        }

        //------------------------------------------------------------------------------------------
        //------------------- Prepopulate database -------------------------------------------------
        //------------------------------------------------------------------------------------------

        private fun prepopulate(context: Context): Callback {
            return object : Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val propertyDao = getInstance(context).propertyDao
                    val agentDao = getInstance(context).agentDao

                    //--------- Agents -------------------------------------------------------------
                    val agents: ArrayList<Agent> = AgentDataSource.createAgentDataSet()
                    for (a in agents){
                        agentDao.createAgent(a)
                    }

                    //--------- Properties ---------------------------------------------------------
                    val properties: ArrayList<Property> = PropertyDataSource.createPropertyDataSet()
                    for(p in properties){
                        propertyDao.createProperty(p)
                    }
                }
            }
        }
    }
}