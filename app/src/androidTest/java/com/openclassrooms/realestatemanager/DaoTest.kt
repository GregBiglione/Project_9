package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.model.HousePhotoDataSource
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class DaoTest {

    //-------------------------------- For data ----------------------------------------------------
    private lateinit var db: RealEstateManagerDatabase

    //-------------------------------- Data set for test -------------------------------------------
    companion object{

        //-------------------------------- Agent data ----------------------------------------------
        val AGENT_TEST1 = Agent(
                1L,
                "https://wallpapertag.com/wallpaper/full/a/2/0/569247-cool-terminator-2-wallpaper-1920x1200.jpg",
                "Schwarzenegger",
                "Arnold",
                "+1 260 222 8751",
                "aschwarzenegger79@gmail.com")

        val AGENT_TEST2 = Agent(
                2L,
                "https://www.everythingaction.com/wp-content/uploads/2011/08/Cobra.1986.BDRip_.720p.01.png",
                "Stallone",
                "Sylvester",
                "+1 530 444 7842",
                "sylvesterstallonecobra@gmail.com")

        const val PHONE_UPDATED = "+1 440 999 2108"

        //-------------------------------- House data ----------------------------------------------
        val HOUSE_TEST1 = House(
                1L,
                HousePhotoDataSource.createHousePhotoDataSet1(),
                "Penthouse",
                "Flatiron District",
                "6 W 22nd St, New York, NY 10010",
                40.7411,
                -73.9910,
                14540000,
                313,
                12,
                3,
                3,
                "Massive half-floor penthouse with 176m2 square foot terrace features a wall of curved glass windows",
                "Available",
                "Sea, Shops, Park",
                1597788000000,
                null,
                1L
        )

        val HOUSE_TEST2 = House(
                2L,
                HousePhotoDataSource.createHousePhotoDataSet2(),
                "Apartment",
                "Wall Street",
                "48 Wall St 11 floor, New York, NY 10005",
                40.7063,
                -74.0091,
                17730800,
                769,
                17,
                7,
                5,
                "New York's most sophisticated, stunning and elegant penthouse.",
                "Available",
                "Metro, Shops, Park",
                1603144800000,
                null,
                2L
        )

        const val POI_4 = "Sea, Shops, Park, Museum"
    }

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
        RealEstateManagerDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    @Throws(Exception::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun createAndGetAllAgents(){
        // Before add agent
        db.agentDao.createAgent(AGENT_TEST1)
        db.agentDao.createAgent(AGENT_TEST2)
        // Test
        val agents = LiveDataTestUtil.getValue(db.agentDao.getAllAgents())
        assertEquals(2, agents.size)
    }

    @Test
    @Throws(Exception::class)
    fun createAndUpdateAgent(){
        // Before add agent
        db.agentDao.createAgent(AGENT_TEST1)
        val agentUpdated = AGENT_TEST1
        agentUpdated.phoneNumber = PHONE_UPDATED
        db.agentDao.updateAgent(agentUpdated)
        // Test
        val agents = LiveDataTestUtil.getValue(db.agentDao.getAgent(1))
        assertEquals(agents[0].phoneNumber, PHONE_UPDATED)
    }

    @Test
    @Throws(Exception::class)
    fun createAndDeleteAgent(){
        // Before add agent
        db.agentDao.createAgent(AGENT_TEST1)
        db.agentDao.createAgent(AGENT_TEST2)
        val agentToDelete = AGENT_TEST1
        db.agentDao.deleteAgent(agentToDelete)
        // Test
        val agents = LiveDataTestUtil.getValue(db.agentDao.getAllAgents())
        assertEquals(1, agents.size)
    }

    @Test
    @Throws(Exception::class)
    fun createAndGetAllHouses(){
        // Before add agent before & house
        db.agentDao.createAgent(AGENT_TEST1)
        db.agentDao.createAgent(AGENT_TEST2)
        db.houseDao.createHouse(HOUSE_TEST1)
        db.houseDao.createHouse(HOUSE_TEST2)
        // Test
        val houses = LiveDataTestUtil.getValue(db.houseDao.getAllHouses())
        assertEquals(2, houses.size)
    }

    @Test
    @Throws(Exception::class)
    fun createAndUpdateHouse(){
        // Before add agent before & house
        db.agentDao.createAgent(AGENT_TEST1)
        db.agentDao.createAgent(AGENT_TEST2)
        db.houseDao.createHouse(HOUSE_TEST1)
        db.houseDao.createHouse(HOUSE_TEST2)
        val houseUpdated = HOUSE_TEST1
        houseUpdated.proximityPointsOfInterest = POI_4
        db.houseDao.updateHouse(houseUpdated)
        // Test
        val houses = LiveDataTestUtil.getValue(db.houseDao.getHouse(1))
        assertEquals(houses[0].proximityPointsOfInterest, POI_4)
    }
}