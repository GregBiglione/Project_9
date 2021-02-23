package com.openclassrooms.realestatemanager.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.droidman.ktoasty.KToasty
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var isCurrencyChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //-------------------------------- Menu ----------------------------------------------------
        configureMainActivityViewModel()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_add_agent, R.id.nav_settings), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Menu --------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.action_search -> {
                KToasty.success(this, "Click on  item " + item.title, Toast.LENGTH_SHORT, true).show()
                goToSearchFragment()
                true
            }
            R.id.action_money -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Change icon $ to € ------------------------------------------
    //----------------------------------------------------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    fun changeCurrencyIcon(item: MenuItem?){
        val dollar: Int = R.drawable.ic_baseline_dollar_white_24
        val euro: Int = R.drawable.ic_baseline_euro_white_24

        if (item!!.icon.constantState!! == resources.getDrawable(dollar, null).constantState){
            item.setIcon(euro)
            mainActivityViewModel.clickDollarsToEuros()
            showSuccessToast("Currency with live data is € & boolean is: $isCurrencyChanged", Toast.LENGTH_SHORT)
        }
        else{
            item.setIcon(dollar)
            mainActivityViewModel.clickEurosToDollars()
            showSuccessToast("Currency with live data is $ & boolean is: $isCurrencyChanged", Toast.LENGTH_SHORT)
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure MainActivityViewModel -----------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureMainActivityViewModel(){
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.isClickedCurrency().observe(this, Observer {
            isCurrencyChanged = it
        })
    }

    //-------------------------------- Get live data boolean value ---------------------------------

    fun booleanOnCurrencyClick(): Boolean{
        return isCurrencyChanged
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Go to search fragment ---------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun goToSearchFragment() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Get search intent data --------------------------------------
    //----------------------------------------------------------------------------------------------

    fun getSearchIntentData(): Long{
        var agentIdFiltered: Long = 0
        if (intent != null) {
            val agentId = intent.extras!!.get("agentId")
            agentIdFiltered = agentId as Long

        }
        return agentIdFiltered
    }
}