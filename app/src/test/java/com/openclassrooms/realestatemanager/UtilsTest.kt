package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.Assert.assertEquals
import org.junit.Test
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
}