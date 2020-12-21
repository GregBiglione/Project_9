package com.openclassrooms.realestatemanager.ui.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showSuccessToast
import com.droidman.ktoasty.showWarningToast
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentSpinnerAdapter
import com.openclassrooms.realestatemanager.adapters.HousePhotoAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.events.DeleteHousePhotoEvent
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.HousePhoto
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class AddHouseActivity : AppCompatActivity() {

    private lateinit var housePhotoRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var housePhotoAdapter: HousePhotoAdapter
    private lateinit var housePhoto: ImageView
    private lateinit var housePhotoDescriptionEditText: TextInputEditText
    //------------------- Photo from gallery code --------------------------------------------------
    private val IMAGE_PICK_CODE = 2108
    private val IMAGE_PERMISSION_CODE = 1201
    //------------------- Button -------------------------------------------------------------------
    private lateinit var addHousePhotoButton: Button
    //------------------- Edit text ----------------------------------------------------------------
    private lateinit var houseDescriptionEditText: TextInputEditText
    private lateinit var houseAddressEditText: TextInputEditText
    private lateinit var housePriceEditText: TextInputEditText
    private lateinit var houseSurfaceEditText: TextInputEditText
    private lateinit var houseRoomsEditText: TextInputEditText
    private lateinit var houseBathRoomsEditText: TextInputEditText
    private lateinit var houseBedRoomsEditText: TextInputEditText
    private lateinit var houseSaleDate: TextInputEditText
    //------------------- Spinner ------------------------------------------------------------------
    private lateinit var houseTypeSpinner: Spinner
    private lateinit var neighborSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var agentsSpinner: Spinner
    //------------------- Checkbox -----------------------------------------------------------------
    private lateinit var pointsOfInterests: TextInputEditText
    private lateinit var listOfPointsOfInterests: Array<String?>
    private lateinit var checkedPointsOfInterests: BooleanArray
    private var poi: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)
        housePhoto = findViewById(R.id.add_house_photo)
        clickOnAddHouseButton()
        configureViewModel()
        configureHousePhotoRecyclerView()
        clickOnAddHousePhotoButton()
        typeSpinner()
        neighborhoodSpinner()
        statusSpinner()
        clickOnPointsOfInterestsEditText() //<--
        agentsSpinner()
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
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val agentDao = RealEstateManagerDatabase.getInstance(applicationContext).agentDao
        val propertyDao = RealEstateManagerDatabase.getInstance(applicationContext).houseDao
        val housePhotoDao = RealEstateManagerDatabase.getInstance(applicationContext).housePhotoDao
        val agentRepository = AgentRepository(agentDao)
        val propertyRepository = HouseRepository(propertyDao)
        val housePhotoRepository = HousePhotoRepository(housePhotoDao)
        val factory = ViewModelFactory(agentRepository, propertyRepository, housePhotoRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        //------------------- Get house photos from room db ----------------------------------------------
        mainViewModel.allHousePhotos.observe(this, { housePhoto ->
            housePhotoAdapter.setData(housePhoto)
        })
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add house photo from gallery ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clickOnAddHouseButton(){
        housePhoto.setOnClickListener { checkPermission() }
    }

    //------------------- Check permission ---------------------------------------------------------

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(application, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                // Permission denied
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                // Show popup permission request
                requestPermissions(permissions, IMAGE_PERMISSION_CODE)
            }
            else{
                // Permission already granted
                pickHousePhotoFromGallery()
            }
        }
        else{
            // System OS < Marshmallow
            pickHousePhotoFromGallery()
        }
    }

    //------------------- Handle permission result -----------------------------------

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            IMAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission from popup granted
                    pickHousePhotoFromGallery()
                } else {
                    // Permission from popup denied
                    applicationContext.showWarningToast("Permission denied", Toast.LENGTH_SHORT, true)
                }
            }
        }
    }

    //------------------- Intent to access gallery -------------------------------------------------

    private fun pickHousePhotoFromGallery() {
        val accessGallery = Intent(Intent.ACTION_PICK)
        accessGallery.type ="image/*"
        startActivityForResult(accessGallery, IMAGE_PICK_CODE)
    }

    //------------------- Handle image pick result -----------------------------------

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            housePhoto.setImageURI(data?.data)
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add house photo in room db -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on add house photo button ------------------------------------------

    private fun clickOnAddHousePhotoButton(){
        addHousePhotoButton = findViewById(R.id.add_house_add_photo_button)
        addHousePhotoButton.setOnClickListener {
            saveHousePhoto()
        }
    }

    private fun saveHousePhoto(){
      housePhotoDescriptionEditText = findViewById(R.id.add_house_photo_description_et)
        val idHousePhoto: Long = System.currentTimeMillis()
        val housePhoto: Uri = Uri.parse(housePhoto.toString())
        val housePhotoDescription: String = housePhotoDescriptionEditText.text.toString().trim()

        if (housePhotoDescription.isEmpty()){
            housePhotoDescriptionEditText.error = getString(R.string.enter_photo_description)
        }
        else{
            addHousePhoto(housePhoto = HousePhoto(idHousePhoto, housePhoto, housePhotoDescription))
        }
    }

    private fun addHousePhoto(housePhoto: HousePhoto){
        mainViewModel.createHousePhoto(housePhoto)
        clearChamps()
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Clear champs -------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clearChamps(){ //Maybe use TextUtils.isEmpty(firstName) & add parameter to method
        if (housePhotoDescriptionEditText.text != null){
            housePhotoDescriptionEditText.setText("")
            housePhoto.setImageResource(R.drawable.ic_baseline_add_a_photo_24)
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Remove house photo from room db ------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Subscribe
    fun onDeleteHousePhoto(event: DeleteHousePhotoEvent){
        mainViewModel.deleteHousePhoto(event.housePhoto)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


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

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Status spinner ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun statusSpinner(){
        val houseStatus = resources.getStringArray(R.array.house_status_array)
        val statusSpinner = findViewById<Spinner>(R.id.add_house_status_spinner)
        if (statusSpinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, houseStatus)
            statusSpinner.adapter = adapter

            statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val statusSelected: String = statusSpinner.selectedItem.toString().trim()
                    showSuccessToast("Status: $statusSelected", Toast.LENGTH_SHORT)
                    //val item = statusSpinner.getItemAtPosition(1)
                    //if (item == 1){
                    //    //houseSaleDate
                    //    showWarningToast("House is: $item", Toast.LENGTH_SHORT)
                    //}
                    //val itemPosition = statusSpinner.selectedItem
                    //val itemPosition = parent?.getItemAtPosition(position)
                    //if (itemPosition == 0){
                    //    showWarningToast("House is: $itemPosition", Toast.LENGTH_SHORT)
                    //}
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Points of interest checkbox ---------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clickOnPointsOfInterestsEditText(){
        pointsOfInterests = findViewById(R.id.add_house_points_of_interests)
        pointsOfInterests.setOnClickListener {
            pointsOfInterestsCheckBox()
        }
    }


    private fun pointsOfInterestsCheckBox(){
        listOfPointsOfInterests = resources.getStringArray(R.array.points_of_interests_array)
        checkedPointsOfInterests = BooleanArray(listOfPointsOfInterests.size)

        //pointsOfInterests = findViewById(R.id.add_house_points_of_interests)
        pointsOfInterests.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.points_of_interests)
            builder.setMultiChoiceItems(listOfPointsOfInterests, checkedPointsOfInterests,
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            if (!poi.contains(which)) {
                                poi.add(which)
                            }
                        }
                        else {
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
                pointsOfInterests.setText(item) // use event if moved in DialogFragment()
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
    //-------------------------------- Agents spinner ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun agentsSpinner(){
        val agentsSpinner = findViewById<Spinner>(R.id.add_house_agent_spinner)
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
                    val selectedAgent: String = agentsSpinner.selectedItem.toString().trim()
                    showSuccessToast("Status: $selectedAgent", Toast.LENGTH_SHORT)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Add house in room db ----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun saveHouse(){
        // house photos uses housePhoto getAllHousePhotos

        houseDescriptionEditText = findViewById(R.id.add_house_description)
        houseAddressEditText = findViewById(R.id.add_house_address)
        housePriceEditText = findViewById(R.id.add_house_price)
        houseSurfaceEditText = findViewById(R.id.add_house_surface)
        houseRoomsEditText = findViewById(R.id.add_house_number_of_rooms)
        houseBathRoomsEditText = findViewById(R.id.add_house_number_of_bathrooms)
        houseBedRoomsEditText = findViewById(R.id.add_house_number_of_bedrooms)

        val id: Long = System.currentTimeMillis()
        //var housePhotoList: List<HousePhoto> = mainViewModel.allHousePhotos
        val typeHouseSelected: String = houseTypeSpinner.selectedItem.toString().trim()
        val neighborhoodSelected: String = neighborSpinner.selectedItem.toString().trim()
        var houseDescription: String = housePhotoDescriptionEditText.text.toString().trim()
        var houseAddress: String = houseAddressEditText.text.toString().trim()
        var houseSurface: String = houseSurfaceEditText.text.toString().trim()
        var houseRooms: String = houseRoomsEditText.text. toString().trim()
        var houseBathRooms: String = houseBathRoomsEditText.text. toString().trim()
        var houseBedRooms: String = houseBedRoomsEditText.text. toString().trim()
        val statusSelected: String = statusSpinner.selectedItem.toString().trim()
        val pointsOfInterestsSelected: String = pointsOfInterests.text.toString().trim()
        val selectedAgent: String = agentsSpinner.selectedItem.toString().trim() //agent id: long in house model
        // set entry date with Today date
        // Set sale date with Date picker
    }
}