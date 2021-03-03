package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showSuccessToast
import com.droidman.ktoasty.showWarningToast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.HouseAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.FilteredHouse
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.ui.activities.AddHouseActivity
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
//import com.openclassrooms.realestatemanager.viewmodel.FilterMainViewModel
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var houseRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var injection: Injection
    //------------------- Data from Search Activity ------------------------------------------------
    private lateinit var typeFilter: String
    private lateinit var neighborhoodFilter: String
    private lateinit var statusFilter: String
    private var agentIdFilter: Long? = 0

    //------------------------ test
    private lateinit var mainActivity: MainActivity
    private var currencyBoolean: Boolean = false
    // 1)
    private val house: ArrayList<House> = ArrayList()
    private var filteredHouse: ArrayList<FilteredHouse> = ArrayList()
    //private val houseAdapter: HouseAdapter = HouseAdapter(currencyBoolean)
    private val houseAdapter: HouseAdapter = HouseAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        houseRecyclerView = root.findViewById(R.id.house_recycler_view)
        val fab: FloatingActionButton = root.findViewById(R.id.add_house_fab)
        fab.setOnClickListener { goToAddActivity() }
        mainActivity = MainActivity()
        injection = Injection()
        //currencyBoolean = mainActivity.booleanOnCurrencyClick()
        // 4)
        configureViewModel()
        //houseAdapter.setData(house)

        configureHouseRecyclerView()
        houseRecyclerView.layoutManager = LinearLayoutManager(activity)
        houseRecyclerView.adapter = houseAdapter
        //houseAdapter.setData(house)
        return root
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure recyclerview ---------------------------------------------------
    //----------------------------------------------------------------------------------------------

    // 6)
    private fun configureHouseRecyclerView(){
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //------------------- Get houses from room db ----------------------------------------------
        mainViewModel.allHouses.observe(viewLifecycleOwner, { h ->
            houseAdapter.setData(h) // suppr when filterHouses will work
            house.clear()
            house.addAll(h)
            filterHouses()
        })
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Filter houses ------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun filterHouses(){
        val bundle = arguments

        if (bundle != null){
            var filter = bundle.getSerializable("filteredHouse")
            //val minPhotos = bundle.getInt("minPhotos")
            //val maxPhotos = bundle.getInt("maxPhotos")
            //val type = bundle.getString("type")
            //val selectedNeighborhood = bundle.getString("neighborhood")
            //val minPrice =  bundle.getInt("minPrice")
            //val maxPrice = bundle.getInt("maxPrice")
            //val minSurface = bundle.getInt("minSurface")
            //val maxSurface = bundle.getInt("maxSurface")
            //val minRooms = bundle.getInt("minRooms")
            //val maxRooms =  bundle.getInt("maxRooms")
            //val minBathrooms = bundle.getInt("minBathrooms")
            //val maxBathrooms = bundle.getInt("maxBathrooms")
            //val minBedrooms = bundle.getInt("minBedrooms")
            //val maxBedrooms = bundle.getInt("maxBedrooms")
            //val selectedStatus = bundle.getString("status")
            //val selectedPoi = bundle.getString("poi")
            //val selectedEntryDate = bundle.getLong("entryDate")
            //val selectedSaleDate = bundle.getLong("saleDate")
            //val selectedAgentId = bundle.getLong("agentId")
            val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
            mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            //mainViewModel.getAllHousesFiltered(filter as ArrayList<FilteredHouse>)
            //mainViewModel.getAllHousesFiltered(/*minPhotos, maxPhotos, type!!, selectedNeighborhood!!, minPrice, maxPrice,
            //minSurface, maxSurface, minRooms, maxRooms, minBathrooms, maxBathrooms, minBedrooms, maxBedrooms, selectedStatus!!,
            //selectedPoi, selectedEntryDate, selectedSaleDate, selectedAgentId*/)
            //        .observe(viewLifecycleOwner, { f ->
            //            house.clear()
            //            house.addAll(f)
            //            houseAdapter.setData(house)
            //        })
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    // 3)
    private fun configureViewModel(){
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Go to add house activity ------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun goToAddActivity(){
        val intent = Intent(context, AddHouseActivity::class.java)
        startActivity(intent)
    }
}