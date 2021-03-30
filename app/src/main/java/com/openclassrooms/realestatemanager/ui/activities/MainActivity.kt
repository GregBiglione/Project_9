package com.openclassrooms.realestatemanager.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.droidman.ktoasty.showErrorToast
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.databinding.ContentMainBinding
import com.openclassrooms.realestatemanager.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener /*1 ---> l 205*/ {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var isCurrencyChanged: Boolean = false
    private var isClickedRefresh: Boolean = false
    private lateinit var bottomNavigationView: BottomNavigationView
    //-------------------------------- Navigation for XL landscape screen --------------------------
    private lateinit var navController: NavController
    //private lateinit var navControllerXL: NavController
    //private lateinit var binding: ContentMainBinding
    private lateinit var binding: ActivityMainBinding
    //private lateinit var drawerLayout: DrawerLayout
    //private lateinit var navView: NavigationView
    private lateinit var navHost: NavHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //-------------------------------- Menu ----------------------------------------------------
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        configureMainActivityViewModel()
        refreshHouseList()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        //4
        //binding = ContentMainBinding.inflate(layoutInflater) // ---> l 219
        //binding = ActivityMainBinding.inflate(layoutInflater)
        navController = findNavController(R.id.nav_host_fragment)
        //3
        //navControllerXL.addOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)  // ---> l 67
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_add_agent, R.id.nav_settings), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //6
        initXLLandscapeScreen() // --------> 6* HomeFragment l60
        filteredHouses()
        navigationBottomMenu()
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
            R.id.action_refresh -> {
                mainActivityViewModel.clickToRefresh()
                true
            }
            R.id.action_search -> {
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
        //val navController = findNavController(R.id.nav_host_fragment)
        //return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        navController = findNavController(R.id.nav_host_fragment)
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
        }
        else{
            item.setIcon(dollar)
            mainActivityViewModel.clickEurosToDollars()
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure MainActivityViewModel -----------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureMainActivityViewModel(){
        mainActivityViewModel.isClickedCurrency().observe(this, Observer {
            isCurrencyChanged = it
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Refresh house list ------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun refreshHouseList(){
        mainActivityViewModel.isClickedRefresh().observe(this, Observer {
            isClickedRefresh = it
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Go to search fragment ---------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun goToSearchFragment() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Filtered house data -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun filteredHouses(){
        if (intent.hasExtra("filteredHouse")){
            val bundle = Bundle()
            bundle.putParcelable("filteredHouse", intent.getParcelableExtra("filteredHouse"))
            navController.navigate(R.id.nav_home, bundle)
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------- Bottom Navigation Menu -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun navigationBottomMenu() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                navController.navigate(R.id.nav_home)
            }
            R.id.nav_map -> {
                navController.navigate(R.id.nav_map)
            }
        }
        true
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Navigation for XL landscape screen --------------------------
    //----------------------------------------------------------------------------------------------

    //2
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when(destination.id){
            R.id.detailedHouseFragment -> {
                supportActionBar?.setHomeButtonEnabled(!resources.getBoolean(R.bool.isLandscape))
                supportActionBar?.setDisplayHomeAsUpEnabled(!resources.getBoolean(R.bool.isLandscape))
            }
            else -> {
                supportActionBar?.setHomeButtonEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(false) // ---> l 72
            }
        }
    }

    //5
    private fun initXLLandscapeScreen(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) //as NavHostFragment
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        //navControllerXL = navHostFragment?.findNavController()!!
        navController = navHostFragment?.findNavController()!!  // ---> l 80
    }
}