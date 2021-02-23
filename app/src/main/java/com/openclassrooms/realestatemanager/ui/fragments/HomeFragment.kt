package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.HouseAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.ui.activities.AddHouseActivity
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var houseRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var houseAdapter: HouseAdapter

    //------------------------ test
    private lateinit var mainActivity: MainActivity
    //private var currencyBoolean: Boolean = false
    // 1)
    //private val house: ArrayList<House> = ArrayList<House>()
    //private val adapter: HouseAdapter = HouseAdapter()
    private lateinit var injection: Injection

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
        configurePropertyRecyclerView()
        // 5)
        //houseAdapter = HouseAdapter()
        //houseAdapter.setData(house)
        //houseRecyclerView = root.findViewById(R.id.house_recycler_view)
        //houseRecyclerView.layoutManager = LinearLayoutManager(activity)
        //houseRecyclerView.adapter = houseAdapter


        return root
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure recyclerview ---------------------------------------------------
    //----------------------------------------------------------------------------------------------

    // 6)
    private fun configureHouseRecyclerView(){
        //mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        //------------------- Get houses from room db ----------------------------------------------
        //mainViewModel.allHouses.observe(viewLifecycleOwner, { house ->
        //    houseAdapter.setData(house)
        //})
    }
    private fun configurePropertyRecyclerView(){
        houseAdapter = HouseAdapter(/*mainActivity, currencyBoolean*/)
        houseRecyclerView.adapter = houseAdapter
        houseRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    // 3)
    private fun configureViewModel(){
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //------------------- Get houses from room db ----------------------------------------------
        mainViewModel.allHouses.observe(viewLifecycleOwner, { house ->
            houseAdapter.setData(house)
        })
    }
    //private fun configureViewModel(){
    //    val agentDao = RealEstateManagerDatabase.getInstance(requireContext()).agentDao
    //    val houseDao = RealEstateManagerDatabase.getInstance(requireContext()).houseDao
    //    val housePhotoDao = RealEstateManagerDatabase.getInstance(requireContext()).housePhotoDao
    //    val agentRepository = AgentRepository(agentDao)
    //    val houseRepository = HouseRepository(houseDao)
    //    val housePhotoRepository = HousePhotoRepository(housePhotoDao)
    //    val factory = ViewModelFactory(agentRepository, houseRepository, housePhotoRepository)
    //    mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    //    //------------------- Get houses from room db ----------------------------------------------
    //    mainViewModel.allHouses.observe(viewLifecycleOwner, { house ->
    //        houseAdapter.setData(house)
    //    })
    //    //-------------------------      TEST             --------------------------------------------------------------------------------------------
    //    //mainViewModel.getAllHousesFiltered(mainActivity.getSearchIntentData())
    //}

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Go to add house activity ------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun goToAddActivity(){
        val intent = Intent(context, AddHouseActivity::class.java)
        startActivity(intent)
    }
}