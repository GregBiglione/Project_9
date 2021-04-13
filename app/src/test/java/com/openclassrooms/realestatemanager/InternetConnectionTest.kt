package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowNetworkInfo


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [25])
class InternetConnectionTest {

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager

    @Before
    fun setUp(){
        context = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .resume()
                .get()
        connectivityManager = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .resume()
                .get()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Test
    fun internetIsDisconnected() {
        val shadowCM: ShadowNetworkInfo = shadowOf(connectivityManager.activeNetworkInfo)//Robolectric.shadowOf(cm)
        shadowCM.setConnectionStatus(NetworkInfo.State.DISCONNECTED)
        assertFalse(Utils.isInternetAvailableNew(context))
    }

    @Test
    fun internetIsConnected() {
        val shadowCM: ShadowNetworkInfo = shadowOf(connectivityManager.activeNetworkInfo)//Robolectric.shadowOf(cm)
        shadowCM.setConnectionStatus(NetworkInfo.State.CONNECTED)
        assertTrue(Utils.isInternetAvailableNew(context))
    }

}