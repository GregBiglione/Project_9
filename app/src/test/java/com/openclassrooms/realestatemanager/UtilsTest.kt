package com.openclassrooms.realestatemanager

import android.content.Context
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment.application
import java.util.*


class UtilsTest {

    //-------------------------------- Data set for test -------------------------------------------
    companion object{
        const val DOLLARS_PRICE = 100.0
        const val EUROS_PRICE = 81.0
    }

    @Test
    @Throws(Exception::class)
    fun convertDollars_To_Euros(){
        assertEquals(EUROS_PRICE, Utils.convertDollarToEuroDouble(DOLLARS_PRICE))
    }

    @Test
    @Throws(Exception::class)
    fun convertEuros_To_Dollars(){
        assertEquals(DOLLARS_PRICE, Utils.convertEuroToDollarDouble(EUROS_PRICE))
    }

    @Test
    @Throws(Exception::class)
    fun convertUsDate_To_FrenchDate(){
        val longDate = 1607295600000
        val date = Date(longDate)
        assertEquals("07/12/2020", Utils.convertUsDateToFrenchDate(date))
    }


    //@Before
    //fun before(){
    //    val cm = Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE)
    //}
    //@Test
    //@Throws(Exception::class)
    //fun check_Internet_isAvailable(){
    //    // TEST 4
    //    val context = mock(Context::class.java)
    //    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    //    if(networkInfo != null){
    //        assertTrue("Wifi is available", Utils.isInternetAvailableNew(context))
    //    }
//
    //    // TEST 3
    //    //val context = mock(Context::class.java)
    //    //val connectivityManager = mock(ConnectivityManager::class.java) //context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    //val networkInfo = connectivityManager.activeNetwork
    //    ////val networkInfo = mock(NetworkInfo::class.java)//connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    //    ////`when`(networkInfo.isConnected).thenReturn(true)
    //    //`when`(networkInfo.isConnected).thenReturn(true)
    //    //`when`(!networkInfo.isConnected).thenReturn(false)
//
    //    // TEST 2
    //    //val context: Context = mock(Context::class.java)
    //    //val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    //val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    //    //`when`(networkInfo.isConnected).thenReturn(true)
    //    //`when`(!networkInfo.isConnected).thenReturn(false)
//
    //    //val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    //    //val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    // TEST 1
    //    //val context = mock(Context::class.java)
    //    //val connectivityManager = mock(ConnectivityManager::class.java) //context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    //val networkInfo = mock(NetworkInfo::class.java)//connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    //    ////`when`(networkInfo.isConnected).thenReturn(true)
    //    //`when`(networkInfo.isConnected).thenReturn(true)
    //    //`when`(!networkInfo.isConnected).thenReturn(false)
    //}


}