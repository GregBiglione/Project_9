package com.openclassrooms.realestatemanager.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showSuccessToast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.HousePhotoAdapter

class AddHouseActivity : AppCompatActivity() {

    private lateinit var housePhotoRecyclerView: RecyclerView
    private lateinit var housePhotoAdapter: HousePhotoAdapter
    private lateinit var housePhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)
        clickOnAddHouseButton()
        configureHousePhotoRecyclerView()
        typeSpinner()
        neighborhoodSpinner()
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure agent recyclerview ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureHousePhotoRecyclerView(){
        housePhotoRecyclerView = findViewById(R.id.add_house_photo_rv)
        housePhotoAdapter = HousePhotoAdapter()
        housePhotoRecyclerView.adapter = housePhotoAdapter
        housePhotoRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show house photo dialog box ---------------------------------
    //----------------------------------------------------------------------------------------------

    fun clickOnAddHouseButton(){
        housePhoto = findViewById(R.id.add_house_photo)
        housePhoto.setOnClickListener {showSuccessToast("Click on add photo icon", Toast.LENGTH_SHORT) }
    }

    //private fun showHousePhotoDialogBox() {
    //    AddHousePhotoDialog(this).show()
    //}


    //----------------------------------------------------------------------------------------------
    //-------------------------------- House type spinner ------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun typeSpinner(){
        val houseType = resources.getStringArray(R.array.house_type)
        val houseTypeSpinner = findViewById<Spinner>(R.id.add_house_type_spinner)
        if (houseTypeSpinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, houseType)
            houseTypeSpinner.adapter = adapter

            houseTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val typeHouseSelected: String = houseTypeSpinner.selectedItem.toString().trim()
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
        val neighborhood = resources.getStringArray(R.array.house_neighborhood)
        val neighborSpinner = findViewById<Spinner>(R.id.add_house_neighborhood_spinner)
        if (neighborSpinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, neighborhood)
            neighborSpinner.adapter = adapter

            neighborSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val neighborhoodSelected: String = neighborSpinner.selectedItem.toString().trim()
                    showSuccessToast("Neighborhood selected: $neighborhoodSelected", Toast.LENGTH_SHORT)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}