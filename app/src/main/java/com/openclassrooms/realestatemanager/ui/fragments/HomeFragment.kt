package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.HouseAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.ui.activities.AddHouseActivity
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var houseRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    //private lateinit var houseAdapter: HouseAdapter
    //------------------- Data from Search Activity ------------------------------------------------
    private lateinit var typeFilter: String
    private var agentIdFilter: Long? = 0

    //------------------------ test
    private lateinit var mainActivity: MainActivity
    //private var currencyBoolean: Boolean = false
    // 1)
    private val house: ArrayList<House> = ArrayList()
    private val houseAdapter: HouseAdapter = HouseAdapter()
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

        // 5)
        houseAdapter.setData(house)

        configureHouseRecyclerView()
        houseRecyclerView.layoutManager = LinearLayoutManager(activity)
        houseRecyclerView.adapter = houseAdapter
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
            //houseAdapter.setData(h)
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
            typeFilter = bundle.getString("typeFilter").toString()
            agentIdFilter = bundle.getLong("agentIdFilter")
            mainViewModel.getAllHousesFiltered(agentIdFilter!!)
        }
        houseAdapter.filterData(house) //<-- ????? filter doesn't works, may be problem comes from filterData in HouseAdapter
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