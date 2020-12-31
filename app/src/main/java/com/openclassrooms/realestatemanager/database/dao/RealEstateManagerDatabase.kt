package com.openclassrooms.realestatemanager.database.dao

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.model.*
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
                            .addCallback(roomCallback!!)
                            .build()
                }
                return instance
            }
        }

        //------------------- Callback -------------------------------------------------------------
        private var roomCallback: Callback? = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PrePopulateDbAsyncTask(INSTANCE).execute()
            }
        }

        private class PrePopulateDbAsyncTask(db: RealEstateManagerDatabase?) : AsyncTask<Void?, Void?, Void?>() {
            val agentDao = db?.agentDao
            val houseDao = db?.houseDao
            val housePhotoDao = db?.housePhotoDao

            override fun doInBackground(vararg params: Void?): Void? {
                //--------- Agents -----------------------------------------------------------------
                val agents: ArrayList<Agent> = AgentDataSource.createAgentDataSet()
                for (a in agents){
                    agentDao?.createAgent(a)
                }

                //--------- Houses -----------------------------------------------------------------
                val houses: ArrayList<House> = HouseDataSource.createHouseDataSet()
                for (h in houses){
                    houseDao?.createHouse(h)
                }

                //--------- Houses photos-----------------------------------------------------------
                val housePhotos: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet1()
                for (p in housePhotos){
                    housePhotoDao?.createHousePhoto(p)
                }
                return null
            }
        }
    }
}