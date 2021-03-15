package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.HouseAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.FilteredHouse
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.ui.activities.AddHouseActivity
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.viewmodel.MainActivityViewModel
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel


class HomeFragment : Fragment() {

    private lateinit var houseRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var injection: Injection
    private lateinit var mainActivity: MainActivity
    private val house: ArrayList<House> = ArrayList()

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var isCurrencyChanged: Boolean = false
    private var isClickedRefresh: Boolean = false
    private lateinit var homeFragment: HomeFragment
    private var houseAdapter: HouseAdapter = HouseAdapter(isCurrencyChanged)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        houseRecyclerView = root.findViewById(R.id.house_recycler_view)
        val fab: FloatingActionButton = root.findViewById(R.id.add_house_fab)
        fab.setOnClickListener { goToAddActivity() }
        //mainActivity = MainActivity()
        injection = Injection()
        configureViewModel()
        configureHouseRecyclerView()
        houseRecyclerView.layoutManager = LinearLayoutManager(activity)
        houseRecyclerView.adapter = houseAdapter
        //------------------- Currency view model --------------------------------------------------
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        configureMainActivityViewModel()
        return root
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure recyclerview ---------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureHouseRecyclerView(){
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //------------------- Get houses from room db ----------------------------------------------
        mainViewModel.allHouses.observe(viewLifecycleOwner, { h ->
            houseAdapter.setData(h)
            house.clear()
            house.addAll(h)
            filterHouses()
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure MainActivityViewModel -----------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureMainActivityViewModel(){
        mainActivityViewModel.isClickedCurrency().observe(requireActivity(), Observer {
            isCurrencyChanged = it
            houseAdapter = HouseAdapter(isCurrencyChanged)
            houseRecyclerView.adapter = houseAdapter
            configureHouseRecyclerView()
        })
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Filter houses ------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun filterHouses(){
        val bundle = arguments

        if (bundle != null){
            val type = bundle.getString("type")
            if (type != null) {
                mainViewModel.getAllHousesFiltered(type).observe(viewLifecycleOwner, { f ->
                    house.clear()
                    house.addAll(f)
                    houseAdapter.setData(house)
                })
            }
            //val filteredHouse: FilteredHouse? = bundle.getParcelable("filteredHouse")
            //val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
            //mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            //filteredHouse?.let {
            //    mainViewModel.getAllHousesFiltered(filteredHouse.minPhotos, filteredHouse.maxPhotos, filteredHouse.type, filteredHouse.neighborhood,
            //            filteredHouse.minPrice, filteredHouse.maxPrice, filteredHouse.minSurface, filteredHouse.maxSurface, filteredHouse.minRooms,
            //            filteredHouse.maxRooms, filteredHouse.minBathrooms, filteredHouse.maxBathrooms, filteredHouse.minBedrooms, filteredHouse.maxBedrooms,
            //            filteredHouse.status, filteredHouse.poi, filteredHouse.entryDate, filteredHouse.saleDate, filteredHouse.agentId)
            //            .observe(viewLifecycleOwner, { f ->
            //                house.clear()
            //                house.addAll(f)
            //                houseAdapter.setData(house)
            //            })
            //}
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
    //-------------------------------- Refresh house list ------------------------------------------
    //----------------------------------------------------------------------------------------------

    override fun onResume() {
        super.onResume()
        refreshHouseList()
    }

    private fun refreshHouseList(){
        mainActivityViewModel.isClickedRefresh().observe(requireActivity(), Observer {
            isClickedRefresh = it
            fullList()
        })
    }

    private fun fullList(){
        mainViewModel.allHouses.observe(viewLifecycleOwner, { h ->
            houseAdapter.setData(h)
            house.clear()
            house.addAll(h)
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Go to add house activity ------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun goToAddActivity(){
        val intent = Intent(context, AddHouseActivity::class.java)
        startActivity(intent)
    }
}