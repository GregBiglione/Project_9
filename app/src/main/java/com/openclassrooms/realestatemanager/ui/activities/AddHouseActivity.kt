package com.openclassrooms.realestatemanager.ui.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentSpinnerAdapter
import com.openclassrooms.realestatemanager.adapters.HousePhotoAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.events.DeleteHousePhotoEvent
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.model.HousePhoto
import com.openclassrooms.realestatemanager.picker.DatePickerFragment
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.ui.dialog_box.PhotoChoiceDialog
import com.openclassrooms.realestatemanager.utils.ImageConverters
import com.openclassrooms.realestatemanager.utils.SavePhoto
import com.openclassrooms.realestatemanager.utils.TimeConverters
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.house_item.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddHouseActivity : AppCompatActivity(), PhotoChoiceDialog.GalleryListener, PhotoChoiceDialog.CameraListener {

    private lateinit var housePhotoRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var housePhotoImageView: ImageView
    private var housePhotoList = generateHousePhotoList()
    private var housePhotoAdapter = HousePhotoAdapter(housePhotoList)
    private lateinit var housePhotoDescriptionEditText: TextInputEditText
    //------------------- Button -------------------------------------------------------------------
    private lateinit var addHousePhotoButton: Button
    private lateinit var addHouseButton: Button
    //------------------- Text input layout --------------------------------------------------------
    private lateinit var houseSaleDateInputLyt: TextInputLayout
    //------------------- Edit text ----------------------------------------------------------------
    private lateinit var houseDescriptionEditText: TextInputEditText
    private lateinit var houseAddressEditText: TextInputEditText
    private lateinit var housePriceEditText: TextInputEditText
    private lateinit var houseSurfaceEditText: TextInputEditText
    private lateinit var houseRoomsEditText: TextInputEditText
    private lateinit var houseBathRoomsEditText: TextInputEditText
    private lateinit var houseBedRoomsEditText: TextInputEditText
    private lateinit var houseEntryDate: TextInputEditText
    private lateinit var houseSaleDate: TextInputEditText
    //------------------- Spinner ------------------------------------------------------------------
    private lateinit var houseTypeSpinner: Spinner
    private lateinit var neighborSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var agentsSpinner: Spinner
    private var selectedAgentId: Long = 0
    //------------------- Checkbox -----------------------------------------------------------------
    private lateinit var pointsOfInterests: TextInputEditText
    private lateinit var listOfPointsOfInterests: Array<String?>
    private lateinit var checkedPointsOfInterests: BooleanArray
    private var poi: ArrayList<Int> = ArrayList()
    //------------------- Uri to bitmap Conversion -------------------------------------------------
    private lateinit var savePhoto: SavePhoto
    private lateinit var imageConverters: ImageConverters
    private var photoFromStorage: Uri? = null
    private lateinit var housePhotoDescriptionInRecyclerView: String

    private val TAG = "AddHouseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)
        housePhotoImageView = findViewById(R.id.add_house_photo)
        housePhotoDescriptionEditText = findViewById(R.id.add_house_photo_description_et)
        agentsSpinner = findViewById(R.id.add_house_agent_spinner)
        housePhotoRecyclerView = findViewById(R.id.add_house_photo_rv)
        houseTypeSpinner = findViewById(R.id.add_house_type_spinner)
        houseDescriptionEditText = findViewById(R.id.add_house_description)
        houseAddressEditText = findViewById(R.id.add_house_address)
        housePriceEditText = findViewById(R.id.add_house_price)
        houseSurfaceEditText = findViewById(R.id.add_house_surface)
        houseRoomsEditText = findViewById(R.id.add_house_number_of_rooms)
        houseBathRoomsEditText = findViewById(R.id.add_house_number_of_bathrooms)
        houseBedRoomsEditText = findViewById(R.id.add_house_number_of_bedrooms)
        clickOnAddHouseImageView()
        configureViewModel()
        configureHousePhotoRecyclerView()
        imageConverters = ImageConverters()
        savePhoto = SavePhoto()
        clickOnAddHousePhotoButton()
        typeSpinner()
        neighborhoodSpinner()
        statusSpinner()
        todayDate()
        saleDate()
        clickOnPointsOfInterestsEditText()
        agentsSpinner()
        //formatPrice()
        clickOnAddHouse()
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure house photo recyclerview ---------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureHousePhotoRecyclerView(){
        housePhotoRecyclerView = findViewById(R.id.add_house_photo_rv)
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
        //mainViewModel.allHousePhotos.observe(this, { housePhoto ->
        //    housePhotoAdapter.setData(housePhoto)
        //})
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add house photo from dialog box ------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clickOnAddHouseImageView(){
        housePhotoImageView.setOnClickListener { showPhotoChoiceDialogBox() }
    }

    //------------------- Show dialog box ----------------------------------------------------------

    private fun showPhotoChoiceDialogBox(){
        val photoChoiceDialog = PhotoChoiceDialog(this, this)
        photoChoiceDialog.show(supportFragmentManager, "Photo choice dialog box")
    }

    //------------------- Get Uri from dialog box --------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyGalleryPhoto(uriPhoto: Uri?) {
        housePhotoImageView.setImageURI(uriPhoto)
        val bitmap = imageConverters.uriToBitmap(uriPhoto, this)
        val tempUri: Uri? = savePhoto.getImageUri(this, bitmap)
        photoFromStorage = tempUri
    }

    //------------------- Get Bitmap from dialog box -----------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyCameraPhoto(bitmapPhoto: Bitmap) {
        housePhotoImageView.setImageBitmap(bitmapPhoto)
        val tempUri: Uri? = savePhoto.getImageUri(this, bitmapPhoto)
        photoFromStorage = tempUri
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Convert price 15000000 to 15,000,000 -------------------------------------
    //----------------------------------------------------------------------------------------------

    //private fun formatPrice(){
    //    housePriceEditText.addTextChangedListener(priceWatcher)
    //}

    //------------------- Price watcher -------------------------------------------------------------

    //private val priceWatcher = object : TextWatcher {
    //    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
    //    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
    //    override fun afterTextChanged(s: Editable?) {
//
    //        //------------------- StringBuilder to hold all digits of the edit text ----------------
    //        val digits = StringBuilder()
    //        //------------------- StringBuilder to hold price --------------------------------------
    //        val priceDigits = StringBuilder()
    //        //------------------- Get characters from teh edit text --------------------------------
    //        val chars: CharArray = housePriceEditText.text.toString().toCharArray()
    //        //------------------- Get each digit with a for loop -----------------------------------
    //        for (x in chars.indices){
    //            //------------------- Add digit into digits StringBuilder --------------------------
    //            digits.append(chars[x])
    //        }
//
    //        //------------------- Add period between digit's phone number --------------------------
    //        if (digits.toString().length >= 4){
    //            //------------------- Thousands ----------------------------------------------------
    //            var thousands = String()
    //            thousands += digits.toString().substring(0,1) + "," + digits.toString().substring(1, 4) //--> 1,234
    //            priceDigits.append(thousands)
    //            //hundred thousands
    //            if (digits.toString().length > 5){
    //                var tenThousands = String()
    //                tenThousands += digits.toString().substring(0,1) + "" + digits.toString().substring(0,1) + "," + digits.toString().substring(2, 5)
    //                // hundredThousands += digits.toString().substring(4, 5) + "," --> 1,,234,
    //                // hundredThousands += digits.toString().substring(4, 5) --> 1,,234
    //                // hundredThousands += digits.toString().substring(1, 5) --> 1,,23,234
    //                //hundredThousands += digits.toString().substring(2) --> 1,,232345
    //                // hundredThousands += digits.toString().substring(0,2) + "," + digits.toString().substring(2, 5) --> 1,,231,,234
    //                // hundredThousands += digits.toString().substring(0,2) + digits.toString().substring(2, 5) --> 1,,231,234
    //                //  hundredThousands += digits.toString().substring(0,2) --> 1,,231,
    //                // hundredThousands += digits.toString().substring(0,5) --> 1,,231,234
    //                // hundredThousands += digits.toString().substring(3,5) --> 1,,2334
    //                //hundredThousands += digits.toString().substring(2,5) --> 1,,23234
    //                // hundredThousands += "a" + digits.toString().substring(3,5) --> 1,,23a34
    //                // hundredThousands += digits.toString().substring(0,2) + "a" + digits.toString().substring(2,5) --> 1,,231,a234
    //                priceDigits.append(hundredThousands)
    //            }
    //            ////millions
    //            //else{
    //            //    priceDigits.append(digits.toString().substring(3))
    //            //}
    //            //------------------- Remove watcher if not we'll have an ∞ loop -------------------
    //            housePriceEditText.removeTextChangedListener(this)
    //            //------------------- Set the new text into the EditText ---------------------------
    //            housePriceEditText.setText(priceDigits.toString())
    //            //------------------- Bring the cursor to the end of input -------------------------
    //            housePriceEditText.setSelection(housePriceEditText.text.toString().length)
    //            //------------------- Bring back the watcher and go on listening to change events --
    //            housePriceEditText.addTextChangedListener(this)
    //        }
    //        else{
    //            return
    //        }
    //    }
    //}

    //----------------------------------------------------------------------------------------------
    //------------------- Add house photo in recyclerview ------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on add house photo button ------------------------------------------

    private fun clickOnAddHousePhotoButton(){
        addHousePhotoButton = findViewById(R.id.add_house_add_photo_button)
        addHousePhotoButton.setOnClickListener { addHousePhotoInRecyclerView() }
    }

    private fun generateHousePhotoList(): ArrayList<HousePhoto>{
        return ArrayList()
    }

    private fun addHousePhotoInRecyclerView(){

        val housePhotoDescription: String = housePhotoDescriptionEditText.text.toString().trim()

        val newHousePhoto = HousePhoto(null, photoFromStorage.toString(), housePhotoDescription)

        housePhotoList.add(newHousePhoto)
        housePhotoDescriptionInRecyclerView = housePhotoDescription
        housePhotoAdapter.notifyDataSetChanged()
        clearChamps()
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Clear champs -------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clearChamps(){ //Maybe use TextUtils.isEmpty(firstName) & add parameter to method
        if (housePhotoDescriptionEditText.text != null){
            housePhotoDescriptionEditText.setText("")
            housePhotoImageView.setImageResource(R.drawable.ic_baseline_add_a_photo_24)
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Remove house photo from recyclerview--------------------------------------
    //----------------------------------------------------------------------------------------------

    @Subscribe
    fun onDeleteHousePhoto(event: DeleteHousePhotoEvent) {
        housePhotoList.remove(event.housePhoto)
        housePhotoAdapter.notifyDataSetChanged()
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
        neighborSpinner = findViewById(R.id.add_house_neighborhood_spinner)
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
        statusSpinner = findViewById(R.id.add_house_status_spinner)
        if (statusSpinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, houseStatus)
            statusSpinner.adapter = adapter

            statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val statusSelected: String = statusSpinner.selectedItem.toString().trim()
                    showSuccessToast("Status: $statusSelected", Toast.LENGTH_SHORT)
                    houseSaleDateInputLyt = findViewById(R.id.add_house_sale_date_input)
                    if (statusSelected == getString(R.string.sold)){
                        houseSaleDateInputLyt.visibility = View.VISIBLE
                    }
                    else{
                        houseSaleDateInputLyt.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set entry date ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun todayDate(){
        houseEntryDate = findViewById(R.id.add_house_entry_date)
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd") //Check if ok when passing data
        val currentDate: String = simpleDateFormat.format(Date())
        houseEntryDate.setText(currentDate)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set sold date -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun saleDate(){
        houseSaleDate = findViewById(R.id.add_house_sale_date)
        houseSaleDate.setOnClickListener { showDatePickerDialog()}
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{ year, month, day -> onDateSelected(year, month, day) }
        datePicker.show(supportFragmentManager, "Date picker")
    }

    private fun onDateSelected(year: Int, month: Int, day: Int){
        houseSaleDate.setText("$month/$day/$year")
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

    //-------------------------------- Dialog box multi choices checkbox ---------------------------
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

                    val selectedObject = agentsSpinner.selectedItem as Agent
                    selectedAgentId = selectedObject.id!!
                    showSuccessToast("Status: $selectedAgentId", Toast.LENGTH_SHORT)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Add house in room db ----------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on add button ------------------------------------------------------

    private fun clickOnAddHouse(){
        addHouseButton = findViewById(R.id.add_house_add_button)
        addHouseButton.setOnClickListener {
            saveHouse()
        }
    }

    private fun saveHouse(){

        val housePhotoDescriptionInRecyclerView = ""
        var housePrice = 0
        var houseSurface = 0
        var houseRooms = 0
        var houseBathRooms = 0
        var houseBedRooms = 0

        var houseDescription = ""
        var houseAddress = ""
        var pointsOfInterestsSelected = ""

        if (housePhotoRecyclerView.isNotEmpty()){
            //housePhotoAdapter.setData(housePhotoList)
        }

        val typeHouseSelected: String = houseTypeSpinner.selectedItem.toString().trim()

        if (!houseDescriptionEditText.text.isNullOrEmpty()){
            houseDescription = houseDescriptionEditText.text.toString().trim()
        }

        val neighborhoodSelected: String = neighborSpinner.selectedItem.toString().trim()

        if (!houseAddressEditText.text.isNullOrEmpty()){
            houseAddress = houseAddressEditText.text.toString().trim()
        }

        if (!housePriceEditText.text.isNullOrEmpty()){
            housePrice = Integer.parseInt(housePriceEditText.text.toString())
        }

        if (!houseSurfaceEditText.text.isNullOrEmpty()){
            houseSurface = Integer.parseInt(houseSurfaceEditText.text.toString())
        }

        if (!houseRoomsEditText.text.isNullOrEmpty()){
            houseRooms = Integer.parseInt(houseRoomsEditText.text.toString())
        }

        if (!houseBathRoomsEditText.text.isNullOrEmpty()){
            houseBathRooms = Integer.parseInt(houseBathRoomsEditText.text.toString())
        }

        if (!houseBedRoomsEditText.text.isNullOrEmpty()){
            houseBedRooms = Integer.parseInt(houseBedRoomsEditText.text.toString())
        }

        val statusSelected: String = statusSpinner.selectedItem.toString().trim()
        val entryDate: Long = System.currentTimeMillis()

        if (!pointsOfInterests.text.isNullOrEmpty()){
            pointsOfInterestsSelected = pointsOfInterests.text.toString().trim()
        }

        addHousePhoto(housePhotoList = HousePhoto(null, photoFromStorage.toString(), housePhotoDescriptionInRecyclerView))

        addHouse(house = House(/*id*/null, housePhotoList, typeHouseSelected, neighborhoodSelected, houseAddress, housePrice, houseSurface,
                houseRooms, houseBathRooms, houseBedRooms, houseDescription, statusSelected, pointsOfInterestsSelected, entryDate,
                null, selectedAgentId))
    }

    private fun addHouse(house: House){
        mainViewModel.createHouse(house)
        showSuccessToast("House added with success ", Toast.LENGTH_SHORT, true)
        // Notification instead of KToasty
    }

    private fun addHousePhoto(housePhotoList: HousePhoto){
        mainViewModel.createHousePhoto(housePhotoList)
    }
}