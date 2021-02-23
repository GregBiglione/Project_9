package com.openclassrooms.realestatemanager.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.droidman.ktoasty.showSuccessToast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentSpinnerAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    //------------------- Button -------------------------------------------------------------------
    private lateinit var searchButton: Button
    //------------------- Spinner ------------------------------------------------------------------
    private lateinit var houseTypeSpinner: Spinner
    private lateinit var neighborSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var agentsSpinner: Spinner
    private var selectedAgentId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        configureViewModel()
        clickOnSearchButton()
        //------------------- Spinner --------------------------------------------------------------
        agentsSpinner = findViewById(R.id.search_agent_spinner)
        agentsSpinner()
    }

    //--------------------------------------------------------------------------------
    //------------------- Configure ViewModel ----------------------------------------
    //--------------------------------------------------------------------------------

    private fun configureViewModel(){
        val agentDao = RealEstateManagerDatabase.getInstance(applicationContext).agentDao
        val propertyDao = RealEstateManagerDatabase.getInstance(applicationContext).houseDao
        val housePhotoDao = RealEstateManagerDatabase.getInstance(applicationContext).housePhotoDao
        val agentRepository = AgentRepository(agentDao)
        val propertyRepository = HouseRepository(propertyDao)
        val housePhotoRepository = HousePhotoRepository(housePhotoDao)
        val factory = ViewModelFactory(agentRepository, propertyRepository, housePhotoRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
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

    private fun goBackToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
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