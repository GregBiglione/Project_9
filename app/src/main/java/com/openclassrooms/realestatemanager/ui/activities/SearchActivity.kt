package com.openclassrooms.realestatemanager.ui.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentSpinnerAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.FilteredHouse
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
    //------------------- Checkbox -----------------------------------------------------------------
    private lateinit var pointsOfInterests: TextInputEditText
    private lateinit var listOfPointsOfInterests: Array<String?>
    private lateinit var checkedPointsOfInterests: BooleanArray
    private var poi: ArrayList<Int> = ArrayList()
    private var selectedPoi: String = ""
    //------------------- Date picker --------------------------------------------------------------
    private lateinit var entryDate: TextInputEditText
    private lateinit var saleDate: TextInputEditText
    //------------------- Time converter -----------------------------------------------------------
    private lateinit var timeConverters: TimeConverters
    private var selectedEntryDate: Long = 0
    private var selectedSaleDate: Long = 0
    //------------------- Double seek bar ----------------------------------------------------------
    private lateinit var priceSeekBar: DoubleValueSeekBarView
    private lateinit var photoSeekBar: DoubleValueSeekBarView
    private lateinit var surfaceSeekBar: DoubleValueSeekBarView
    private lateinit var roomsSeekBar: DoubleValueSeekBarView
    private lateinit var bathroomsSeekBar: DoubleValueSeekBarView
    private lateinit var bedroomsSeekBar: DoubleValueSeekBarView

    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
        //-------------------------------- Checkbox poi --------------------------------------------
        pointsOfInterests = findViewById(R.id.search_poi_ti_et)
        pointsOfInterestsCheckBox()
        //------------------- Double seek bar ------------------------------------------------------
        priceSeekBar = findViewById(R.id.search_price_range_seek_bar)
        photoSeekBar = findViewById(R.id.search_photos_double_range_seek_bar)
        surfaceSeekBar = findViewById(R.id.search_surface_double_range_seek_bar)
        roomsSeekBar = findViewById(R.id.search_rooms_double_range_seek_bar)
        bathroomsSeekBar = findViewById(R.id.search_bathrooms_double_range_seek_bar)
        bedroomsSeekBar = findViewById(R.id.search_bedrooms_double_range_seek_bar)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure ViewModel -----------------------------------------
    //----------------------------------------------------------------------------------------------

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
    //-------------------------------- Poi multi checkbox spinner ----------------------------------
    //----------------------------------------------------------------------------------------------

    private fun pointsOfInterestsCheckBox(){
        listOfPointsOfInterests = resources.getStringArray(R.array.points_of_interests_array)
        checkedPointsOfInterests = BooleanArray(listOfPointsOfInterests.size)

        pointsOfInterests.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.points_of_interests)
            builder.setMultiChoiceItems(listOfPointsOfInterests, checkedPointsOfInterests,
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            if (!poi.contains(which)) {
                                poi.add(which)
                            }
                        } else {
                            poi.remove(which)
                        }
                    })
            //-------------------------------- Behavior if you cancel the selection ----------------
            builder.setCancelable(false)
            //-------------------------------- Positive button -------------------------------------
            builder.setPositiveButton(R.string.add, DialogInterface.OnClickListener { dialog, which ->
                var item: String = " "
                for (i in poi.indices) {
                    item += listOfPointsOfInterests[poi[i]]

                    if (i != poi.size - 1) {
                        item = item + ", "
                    }
                }
                pointsOfInterests.setText(item) // use event if moved in DialogFragment() ??
                selectedPoi = pointsOfInterests.text.toString().trim()

            })
            //-------------------------------- Negative button -------------------------------------
            builder.setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            //-------------------------------- Neutral button --------------------------------------
            builder.setNeutralButton(R.string.clear, DialogInterface.OnClickListener { dialog, which ->
                for (i in checkedPointsOfInterests.indices) {
                    checkedPointsOfInterests[i] = false
                    poi.clear()
                    pointsOfInterests.setText("")
                }
            })
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
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
        filterHouses()
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Filter data -------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun filterHouses(){
        val minPhotos = photoSeekBar.currentMinValue
        val maxPhotos = photoSeekBar.currentMaxValue
        val minPrice = priceSeekBar.currentMinValue * 10000
        val maxPrice = priceSeekBar.currentMaxValue * 10000
        val minSurface = surfaceSeekBar.currentMinValue
        val maxSurface = surfaceSeekBar.currentMaxValue
        val minRooms = roomsSeekBar.currentMinValue
        val maxRooms = roomsSeekBar.currentMaxValue
        val minBathrooms = bathroomsSeekBar.currentMinValue
        val maxBathrooms = bathroomsSeekBar.currentMaxValue
        val minBedrooms = bedroomsSeekBar.currentMinValue
        val maxBedrooms = bedroomsSeekBar.currentMaxValue

        val intent = Intent(this, MainActivity::class.java)
        //bundle.putSerializable("filteredHouse", FilteredHouse(minPhotos, maxPhotos, selectedType, selectedNeighborhood, minPrice, maxPrice,
        //minSurface, maxSurface, minRooms, maxRooms, minBathrooms, maxBathrooms, minBedrooms, maxBedrooms, selectedStatus, selectedPoi,
        //selectedEntryDate, selectedSaleDate, selectedAgentId))
        intent.putExtra("minPhotos", minPhotos)
        intent.putExtra("maxPhotos", maxPhotos)
        intent.putExtra("type", selectedType)
        intent.putExtra("neighborhood", selectedNeighborhood)
        intent.putExtra("minPrice", minPrice)
        intent.putExtra("maxPrice", maxPrice)
        intent.putExtra("minSurface", minSurface)
        intent.putExtra("maxSurface", maxSurface)
        intent.putExtra("minRooms", minRooms)
        intent.putExtra("maxRooms", maxRooms)
        intent.putExtra("minBathrooms", minBathrooms)
        intent.putExtra("maxBathrooms", maxBathrooms)
        intent.putExtra("minBedrooms", minBedrooms)
        intent.putExtra("maxBedrooms", maxBedrooms)
        intent.putExtra("status", selectedStatus)
        intent.putExtra("poi", selectedPoi)
        intent.putExtra("entryDate", selectedEntryDate)
        intent.putExtra("saleDate", selectedSaleDate)
        intent.putExtra("agentId", selectedAgentId)
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