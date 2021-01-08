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
                            "Db"
                    )
                            .addCallback(roomCallback!!)
                            .build()
                }
                INSTANCE = instance
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
                val housePhotos1: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet1()
                for (p in housePhotos1){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos2: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet2()
                for (p in housePhotos2){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos3: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet3()
                for (p in housePhotos3){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos4: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet4()
                for (p in housePhotos4){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos5: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet5()
                for (p in housePhotos5){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos6: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet6()
                for (p in housePhotos6){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos7: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet7()
                for (p in housePhotos7){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos8: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet8()
                for (p in housePhotos8){
                    housePhotoDao?.createHousePhoto(p)
                }

                val housePhotos9: ArrayList<HousePhoto> = HousePhotoDataSource.createHousePhotoDataSet9()
                for (p in housePhotos9){
                    housePhotoDao?.createHousePhoto(p)
                }
                return null
            }
        }
    }
}