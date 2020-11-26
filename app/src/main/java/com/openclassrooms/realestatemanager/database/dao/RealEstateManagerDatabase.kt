package com.openclassrooms.realestatemanager.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.PropertyDataSource

@Database(entities = [PropertyDataSource::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase: RoomDatabase() {

    //------------------- DAO ----------------------------------------------------------------------
    abstract fun propertyDao(): PropertyDao
    // add agentDao

    //------------------- Singleton ----------------------------------------------------------------
    companion object{
        @Volatile
        private var INSTANCE: RealEstateManagerDatabase? = null

        fun getDatabase(context: Context): RealEstateManagerDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RealEstateManagerDatabase::class.java,
                        "property_database" // change name
                )
                        .addCallback(prepopulate(context))
                        .build()
                INSTANCE = instance
                return instance
            }
        }

        private fun prepopulate(context: Context): Callback {
            return object: Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val propertyDao = getDatabase(context).propertyDao()

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