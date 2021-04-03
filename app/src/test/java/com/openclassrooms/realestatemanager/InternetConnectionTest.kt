package com.openclassrooms.realestatemanager

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowSettings


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class InternetConnectionTest {

    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp(){
        //val cm = Robolectric.buildActivity((MainActivity::class.java))
        //        .create()
        //        .resume()
        //        .get()
        //        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //
        //val enabled: Boolean = true
        //val context: Context = Robolectric.buildActivity((MainActivity::class.java))
        //        .create()
        //        .resume()
        //        .get()
        //        .applicationContext
    }

}