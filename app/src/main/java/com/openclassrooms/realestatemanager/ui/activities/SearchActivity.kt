package com.openclassrooms.realestatemanager.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentSpinnerAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.picker.SearchDatePickerFragment
import com.openclassrooms.realestatemanager.ui.fragments.HomeFragment
import com.openclassrooms.realestatemanager.utils.TimeConverters
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var injection: Injection
    //------------------- Button -------------------------------------------------------------------
    private lateinit var searchButton: Button
    //------------------- Spinner ------------------------------------------------------------------
    private lateinit var houseTypeSpinner: Spinner
    private lateinit var neighborSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var agentsSpinner: Spinner
    private lateinit var selectedNeighborhood: String
    private lateinit var selectedType: String
    private lateinit var selectedStatus: String
    private var selectedAgentId: Long = 0
    private lateinit var soldDateTitle: TextView
    private lateinit var soldDateLyt: TextInputLayout
    //------------------- Date picker --------------------------------------------------------------
    private lateinit var entryDate: TextInputEditText
    private lateinit var saleDate: TextInputEditText
    //------------------- Time converter -----------------------------------------------------------
    private lateinit var timeConverters: TimeConverters
    private var selectedEntryDate: Long = 0
    private var selectedSaleDate: Long = 0



    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //val fragmentManager: FragmentManager = supportFragmentManager
        //val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        homeFragment = HomeFragment()
        configureViewModel()
        clickOnSearchButton()
        //------------------- Spinner --------------------------------------------------------------
        houseTypeSpinner = findViewById(R.id.search_type_spinner)
        typeSpinner()
        neighborSpinner = findViewById(R.id.search_neighborhood_spinner)
        neighborhoodSpinner()
        statusSpinner = findViewById(R.id.search_status_spinner)
        statusSpinner()
        agentsSpinner = findViewById(R.id.search_agent_spinner)
        agentsSpinner()
        //------------------- Sold date ------------------------------------------------------------
        soldDateTitle = findViewById(R.id.search_sold_title)
        soldDateLyt = findViewById(R.id.search_sale_date_ti_ly)
        entryDate = findViewById(R.id.search_entry_et)
        chooseEntryDate()
        saleDate = findViewById(R.id.search_sale_et)
        choseSaleDate()
        //------------------- Time converter -------------------------------------------------------
        timeConverters = TimeConverters()
        //convertDateStringToLong()
    }

    //--------------------------------------------------------------------------------
    //------------------- Configure ViewModel ----------------------------------------
    //--------------------------------------------------------------------------------

    private fun configureViewModel(){
        injection = Injection()
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- House type spinner ------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun typeSpinner(){
        val houseType = resources.getStringArray(R.array.house_type)
        val houseTypeSpinner = findViewById<Spinner>(R.id.search_type_spinner)
        if (houseTypeSpinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, houseType)
            houseTypeSpinner.adapter = adapter

            houseTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val typeHouseSelected: String = houseTypeSpinner.selectedItem.toString().trim()
                    selectedType = typeHouseSelected
                    showSuccessToast("Type selected: $typeHouseSelected", Toast.LENGTH_SHORT)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Neighborhood spinner ----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun neighborhoodSpinner(){
        neighborSpinner = findViewById(R.id.search_neighborhood_spinner)
        val neighborhood = resources.getStringArray(R.array.house_neighborhood)
        val neighborSpinner = findViewById<Spinner>(R.id.search_neighborhood_spinner)
        if (neighborSpinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, neighborhood)
            neighborSpinner.adapter = adapter

            neighborSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val neighborhoodSelected: String = neighborSpinner.selectedItem.toString().trim()
                    selectedNeighborhood = neighborhoodSelected
                    showSuccessToast("Neighborhood selected: $neighborhoodSelected", Toast.LENGTH_SHORT)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Status spinner ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun statusSpinner(){
        val houseStatus = resources.getStringArray(R.array.house_status_array)
        statusSpinner = findViewById(R.id.search_status_spinner)
        if (statusSpinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, houseStatus)
            statusSpinner.adapter = adapter

            statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val statusSelected: String = statusSpinner.selectedItem.toString().trim()
                    selectedStatus = statusSelected
                    showSuccessToast("Status: $statusSelected", Toast.LENGTH_SHORT)

                    if (statusSelected == getString(R.string.sold)){
                        soldDateTitle.visibility = View.VISIBLE
                        soldDateLyt.visibility = View.VISIBLE
                    }
                    else{
                        soldDateTitle.visibility = View.GONE
                        soldDateLyt.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set entry & sold date ---------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun chooseEntryDate(){
        entryDate.setOnClickListener { showEntryPickerDialogEntryDate() }
    }

    private fun choseSaleDate(){
        saleDate.setOnClickListener { showSalePickerDialogEntryDate() }
    }

    private fun showEntryPickerDialogEntryDate() {
        val datePicker = SearchDatePickerFragment{ day, month, year -> onEntryDateSelected( day, month, year)}
        datePicker.show(supportFragmentManager, "Date picker")
    }

    private fun showSalePickerDialogEntryDate() {
        val datePicker = SearchDatePickerFragment{ day, month, year -> onSaleDateSelected( day, month, year)}
        datePicker.show(supportFragmentManager, "Date picker")
    }

    //-------------------------------- Set entry date ----------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun onEntryDateSelected(day: Int, month: Int, year: Int){
        var dayOfWeek = ""
        val monthOfYear = month + 1
        var monthString = ""

        dayOfWeek = if (day < 10){
            "0$day"
        }
        else{
            day.toString()
        }
        monthString = if (monthOfYear < 10){
            "0$monthOfYear"
        }
        else{
            monthOfYear.toString()
        }

        entryDate.setText("$dayOfWeek/$monthString/$year")
        val entryDateString: String = entryDate.text.toString().trim()
        val entryDateLong: Long = timeConverters.convertDateToLong(entryDateString)
        selectedEntryDate = entryDateLong
    }

    //-------------------------------- Set sale date -----------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun onSaleDateSelected(day: Int, month: Int, year: Int){
        var dayOfWeek = ""
        val monthOfYear = month + 1
        var monthString = ""

        dayOfWeek = if (day < 10){
            "0$day"
        }
        else{
            day.toString()
        }
        monthString = if (monthOfYear < 10){
            "0$monthOfYear"
        }
        else{
            monthOfYear.toString()
        }

        saleDate.setText("$dayOfWeek/$monthString/$year").toString()
        val saleDateString: String = saleDate.text.toString().trim()
        val saleDateLong: Long = timeConverters.convertDateToLong(saleDateString)
        selectedSaleDate = saleDateLong
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Agents spinner ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun agentsSpinner(){
        val agentsSpinner = findViewById<Spinner>(R.id.search_agent_spinner)
        if (agentsSpinner != null){
            val adapter = AgentSpinnerAdapter(this)
            mainViewModel.allAgents.observe(this, androidx.lifecycle.Observer { agent ->
                agent.forEach {
                    adapter.add(it)
                }
            })
            agentsSpinner.adapter = adapter

            agentsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    val selectedObject = agentsSpinner.selectedItem as Agent
                    selectedAgentId = selectedObject.id!!
                    showSuccessToast("Status: $selectedAgentId", Toast.LENGTH_SHORT)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }


    //----------------------------------------------------------------------------------------------
    //-------------------------------- Launch search -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    //-------------------------------- Click on search button --------------------------------------

    private fun clickOnSearchButton(){
        searchButton = findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            searchHouses()
        }
    }

    private fun searchHouses() {
        mainViewModel.getAllHousesFiltered(selectedAgentId)
        //------------------- Back to main activity after search house -----------------------------
        goBackToMainActivity()
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Filter data -------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun filterHouses(){
        val bundle = Bundle()
        bundle.putString("typeFilter", selectedType)
        bundle.putString("neighborhoodFilter", selectedNeighborhood)
        bundle.putString("statusFilter", selectedStatus)
        bundle.putLong("entryDateFilter", selectedEntryDate)
        bundle.putLong("saleDateFilter", selectedSaleDate)
        bundle.putLong("agentIdFilter", selectedAgentId)

        homeFragment.arguments = bundle
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, homeFragment).commit()
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Back to to Main activity after filter -----------------------
    //----------------------------------------------------------------------------------------------

    private fun goBackToMainActivity(){
        filterHouses()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Back button to Main activity --------------------------------
    //----------------------------------------------------------------------------------------------

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}