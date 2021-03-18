package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.provider.HouseContentProvider
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HouseContentProviderTest {

    //-------------------------------- For data ----------------------------------------------------
    private lateinit var contentResolver: ContentResolver

    //-------------------------------- Data set for test -------------------------------------------
    companion object{
        private var HOUSE_ID = 1L
    }

    @Before
    fun setUp(){
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                RealEstateManagerDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun getHousesWhenNoHousesInserted(){
        val cursor: Cursor? = contentResolver.query(ContentUris.withAppendedId(HouseContentProvider.URI_HOUSE, HOUSE_ID), null, null,
        null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        cursor.close()
    }

}