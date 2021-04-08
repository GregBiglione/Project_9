package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkInfo.DetailedState
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.Assert.assertFalse
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowConnectivityManager
import org.robolectric.shadows.ShadowNetworkInfo


@RunWith(RobolectricTestRunner::class)
//@Config(sdk = [25])
@Config(sdk = [25])
class InternetConnectionTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var context: Context

    //@Before
    //fun setUp(){
    //    //val cm = Robolectric.buildActivity((MainActivity::class.java))
    //    //        .create()
    //    //        .resume()
    //    //        .get()
    //    //        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    //
    //    //val enabled: Boolean = true
    //    //val context: Context = Robolectric.buildActivity((MainActivity::class.java))
    //    //        .create()
    //    //        .resume()
    //    //        .get()
    //    //        .applicationContext
    //}

    @Before
    fun setUp(){
        context = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .resume()
                .get()
        val connectivityManager = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .resume()
                .get()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val shadowCM: ShadowNetworkInfo = shadowOf(connectivityManager.activeNetworkInfo)//Robolectric.shadowOf(cm)
        shadowCM.setConnectionStatus(NetworkInfo.State.DISCONNECTED)
        //val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        //val shadowConnectivityManager: ShadowConnectivityManager = Robolectric.
    }

    //@Before
    //fun before() {
    //    //val cm = Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    //val connectivityManager = Robolectric.buildActivity(MainActivity::class.java)
    //    //        .create()
    //    //        .resume()
    //    //        .get()
    //    //        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    //val shadowCM: ShadowNetworkInfo = shadowOf(connectivityManager.activeNetworkInfo)//Robolectric.shadowOf(cm)
    //    //shadowCM.setConnectionStatus(NetworkInfo.State.DISCONNECTED)
    //    //hadowCM.setNetworkInfo(
    //    //       ConnectivityManager.TYPE_MOBILE,
    //    //       ShadowNetworkInfo.newInstance(
    //    //               DetailedState.DISCONNECTED,
    //    //               ConnectivityManager.TYPE_MOBILE,
    //    //               ConnectivityManager.TYPE_MOBILE_MMS,
    //    //               true,
    //    //               false))
    //}

    @Test
    fun mobileIsDisconnected() {
        assertFalse(Utils.isInternetAvailableNew(context))
        //assertThat(Utils.isInternetAvailableNew(context), false)
    }

}