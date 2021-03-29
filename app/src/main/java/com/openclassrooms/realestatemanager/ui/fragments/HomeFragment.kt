package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showErrorToast
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.HouseAdapter
import com.openclassrooms.realestatemanager.databinding.FragmentHomeBinding
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
    //-------------------------------- Navigation for XL landscape screen --------------------------
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var fab: FloatingActionButton
    //-------------------------------- No house after filter search --------------------------------
    private lateinit var noHouseTv: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //if (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_LARGE
        //        && ((resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) != Configuration.SCREENLAYOUT_SIZE_NORMAL)
        //        && (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_SMALL)){
        //    binding = FragmentHomeBinding.inflate(layoutInflater)
        //    houseRecyclerView = binding.root.findViewById(R.id.house_recycler_view)
        //    fab = binding.root.findViewById(R.id.add_house_fab)
        //    fab.setOnClickListener { goToAddActivity() }
        //    //mainActivity = MainActivity()
        //    injection = Injection()
        //    configureViewModel()
        //    configureHouseRecyclerView()
        //    houseRecyclerView.layoutManager = LinearLayoutManager(activity)
        //    houseRecyclerView.adapter = houseAdapter
        //    //------------------- Currency view model --------------------------------------------------
        //    mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        //    activity?.showSuccessToast("XL (home) layout displayed", Toast.LENGTH_SHORT, true)
        //    return binding.root
        //}
        //else{
        //    val view = inflater.inflate(R.layout.fragment_home, container, false)
        //    houseRecyclerView = view.findViewById(R.id.house_recycler_view)
        //    fab = view.findViewById(R.id.add_house_fab)
        //    fab.setOnClickListener { goToAddActivity() }
        //    //mainActivity = MainActivity()
        //    injection = Injection()
        //    configureViewModel()
        //    configureHouseRecyclerView()
        //    houseRecyclerView.layoutManager = LinearLayoutManager(activity)
        //    houseRecyclerView.adapter = houseAdapter
        //    //------------------- Currency view model --------------------------------------------------
        //    mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        //    activity?.showErrorToast("Normal (home) layout displayed", Toast.LENGTH_SHORT, true)
        //    return view
        //}
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
        //-------------------------------- No house after filter search ----------------------------
        noHouseTv = root.findViewById(R.id.home_no_house)
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
            noHouseTv.visibility = View.GONE
            //if (h.isNotEmpty()){
            //    noHouseTv.visibility = View.GONE
            //}
            filterHouses()
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure MainActivityViewModel -----------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureMainActivityViewModel(){
        mainActivityViewModel.isClickedCurrency().observe(viewLifecycleOwner, Observer {
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
            val filteredHouse: FilteredHouse? = bundle.getParcelable("filteredHouse")
            val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
            mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            filteredHouse?.let {
                mainViewModel.getAllHousesFiltered(filteredHouse)
                        .observe(viewLifecycleOwner, { f ->
                            house.clear()
                            house.addAll(f)
                            houseAdapter.setData(house)
                            //showNoHouse()
                            if (f.isEmpty()){
                                noHouseTv.visibility = View.VISIBLE
                            }
                        })
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Refresh house list ------------------------------------------
    //----------------------------------------------------------------------------------------------

    override fun onResume() {
        super.onResume()
        //displayXLScreen()
        configureMainActivityViewModel()
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
            noHouseTv.visibility = View.GONE
            isClickedRefresh = true
            //if (h.isNotEmpty()){
            //    noHouseTv.visibility = View.GONE
            //}
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Go to add house activity ------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun goToAddActivity(){
        val intent = Intent(context, AddHouseActivity::class.java)
        startActivity(intent)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- XL landscape screen -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun displayXLScreen(){
        childFragmentManager.beginTransaction()
                .replace(binding.detailedFragmentContainer!!.id, DetailedHouseFragment())
                .commit()
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show icon if no house found ---------------------------------
    //----------------------------------------------------------------------------------------------

    private fun showNoHouse(){
        if (house.size == 0){
            noHouseTv.visibility = View.VISIBLE
        }
    }

    private fun hideNoHouse(){
        if (house.size != 0){
            noHouseTv.visibility = View.GONE
        }
    }
}