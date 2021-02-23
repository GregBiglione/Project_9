package com.openclassrooms.realestatemanager.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showErrorToast
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentSpinnerAdapter
import com.openclassrooms.realestatemanager.adapters.UpdateHousePhotoAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.events.DeleteHousePhotoEvent
import com.openclassrooms.realestatemanager.injections.Injection
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
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class UpdateHouseFragment : Fragment(), PhotoChoiceDialog.GalleryListener, PhotoChoiceDialog.CameraListener {

    private val args by navArgs<UpdateHouseFragmentArgs>()
    private lateinit var housePhotoRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var injection: Injection
    private lateinit var housePhotoImageView: ImageView
    private var newHousePhotoList = generateHousePhotoList()
    private var oldHousePhotoList = generateHousePhotoList()
    private var housePhotoAdapter = UpdateHousePhotoAdapter(oldHousePhotoList)
    private lateinit var housePhotoDescriptionEditText: TextInputEditText
    //------------------- Button -------------------------------------------------------------------
    private lateinit var addHousePhotoButton: Button
    private lateinit var updateHouseButton: Button
    //------------------- Edit text ----------------------------------------------------------------
    private lateinit var houseDescriptionEditText: TextInputEditText
    private lateinit var houseAddressEditText: TextInputEditText
    private lateinit var housePriceEditText: TextInputEditText
    private lateinit var houseSurfaceEditText: TextInputEditText
    private lateinit var houseRoomsEditText: TextInputEditText
    private lateinit var houseBathRoomsEditText: TextInputEditText
    private lateinit var houseBedRoomsEditText: TextInputEditText
    private lateinit var houseEntryDate: TextInputEditText
    private lateinit var houseSaleDateLyt: LinearLayout
    private lateinit var houseSaleDateEditText: TextInputEditText
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
    //------------------- Time converter -----------------------------------------------------------
    private lateinit var timeConverters: TimeConverters
    //------------------- House type spinner -------------------------------------------------------
    private lateinit var houseTypeSpinner: Spinner
    private lateinit var houseTypeAdapter: ArrayAdapter<String>
    //------------------- Neighborhood spinner -----------------------------------------------------
    private lateinit var neighborhoodSpinner: Spinner
    private lateinit var neighborhoodAdapter: ArrayAdapter<String>
    //------------------- Status spinner -----------------------------------------------------------
    private lateinit var statusSpinner: Spinner
    private lateinit var statusAdapter: ArrayAdapter<String>
    //------------------- Agent spinner ------------------------------------------------------------
    private lateinit var agentsSpinner: Spinner
    private lateinit var agentAdapter: ArrayAdapter<Agent>
    private var selectedAgentId: Long = 0
    //------------------- House photo list elements ------------------------------------------------
    private var housePhotoIdFromList: Long? = null
    private var housePhotoFromList: String = ""
    private var housePhotoDescriptionFromList: String = ""
    //------------------- House photo list elements ------------------------------------------------
    private var saleHouseDate: Long = 0
    //------------------- Test update house --------------------------------------------------------
    //private var upHousePhoto: HousePhoto = null
    //private var upHousePhotoDescription: String = ""
//
    //private var upType: String = "type"
    //private var upDescription: String = "desc"
    //private var upNeighborhood = "neigh"
    //private var upAddress = "address"
    //private var upPrice: Int = 0
    //private var upSurface: Int = 0
    //private var upRooms: Int = 0
    //private var upBathrooms: Int = 0
    //private var upBedrooms: Int = 0
//
    //private var upStatus: String = "satus"
//
    //private var upEntryDate: Long = 0
    //private var upSaleDate: Long = 0
//
    //private var upPoi: String = "poi"
    //private var upAgentId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_house, container, false)
        configureViewModel()
        //------------------- TimeConverters -------------------------------------------------------
        timeConverters = TimeConverters()
        //------------------- Photo image view -----------------------------------------------------
        housePhotoImageView = view.findViewById(R.id.update_add_house_photo)
        imageConverters = ImageConverters()
        savePhoto = SavePhoto()
        clickOnAddHouseImageView()
        //------------------- Photo recycler view --------------------------------------------------
        addHousePhotoButton = view.findViewById(R.id.update_add_house_add_photo_button)
        housePhotoDescriptionEditText = view.findViewById(R.id.update_add_house_photo_description_et)
        housePhotoRecyclerView = view.findViewById(R.id.update_add_house_photo_rv)
        configureHousePhotoRecyclerView()
        clickOnAddHousePhotoButton()
        //------------------- EditTexts ------------------------------------------------------------
        houseDescriptionEditText = view.findViewById(R.id.update_house_description)
        houseAddressEditText = view.findViewById(R.id.update_house_address)
        housePriceEditText = view.findViewById(R.id.update_house_price)
        houseSurfaceEditText = view.findViewById(R.id.update_house_surface)
        houseRoomsEditText = view.findViewById(R.id.update_house_number_of_rooms)
        houseBathRoomsEditText = view.findViewById(R.id.update_house_number_of_bathrooms)
        houseBedRoomsEditText = view.findViewById(R.id.update_house_number_of_bedrooms)
        houseEntryDate = view.findViewById(R.id.update_house_entry_date)
        houseSaleDateLyt = view.findViewById(R.id.update_sale_date_lyt)
        //------------------- House type spinner ---------------------------------------------------
        houseTypeSpinner = view.findViewById(R.id.update_add_house_type_spinner)
        val houseType = resources.getStringArray(R.array.house_type)
        houseTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, houseType)
        //------------------- Neighborhood spinner -------------------------------------------------
        neighborhoodSpinner = view.findViewById(R.id.update_add_house_neighborhood_spinner)
        val neighborhood = resources.getStringArray(R.array.house_neighborhood)
        neighborhoodAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, neighborhood)
        //------------------- Status spinner -------------------------------------------------------
        statusSpinner = view.findViewById(R.id.update_house_status_spinner)
        val houseStatus = resources.getStringArray(R.array.house_status_array)
        statusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, houseStatus)
        //------------------- Agent spinner ------------------------------------------------------------
        agentsSpinner = view.findViewById<Spinner>(R.id.update_house_agent_spinner)
        agentAdapter = AgentSpinnerAdapter(requireContext())
        //-------------------------------- Entry & sale date ---------------------------------------
        houseEntryDate = view.findViewById(R.id.update_house_entry_date)
        houseSaleDateEditText = view.findViewById(R.id.update_house_sale_date)
        //-------------------------------- Checkbox poi --------------------------------------------
        pointsOfInterests = view.findViewById(R.id.update_house_points_of_interests)
        //-------------------------------- Update button -------------------------------------------
        updateHouseButton = view.findViewById(R.id.update_house_button)
        fillEditTexts()
        showSaleDate()
        typeSpinner()
        neighborhoodSpinner()
        statusSpinner()
        entryDate()
        saleDate()
        clickOnPointsOfInterestsEditText()
        agentsSpinner()
        clickOnUpdateHouseButton()
        return view
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
        photoChoiceDialog.show(parentFragmentManager, "Photo choice dialog box")
        photoChoiceDialog.setTargetFragment(this, 2)
    }

    //------------------- Get Uri from dialog box --------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyGalleryPhoto(uriPhoto: Uri?) {
        housePhotoImageView.setImageURI(uriPhoto)
        val bitmap = imageConverters.uriToBitmap(uriPhoto, requireContext())
        val tempUri: Uri? = savePhoto.getImageUri(requireContext(), bitmap)
        photoFromStorage = tempUri
    }

    //------------------- Get Bitmap from dialog box -----------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyCameraPhoto(bitmapPhoto: Bitmap) {
        housePhotoImageView.setImageBitmap(bitmapPhoto)
        val tempUri: Uri? = savePhoto.getImageUri(requireContext(), bitmapPhoto)
        photoFromStorage = tempUri
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add house photo in recyclerview ------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on add house photo button ------------------------------------------

    private fun clickOnAddHousePhotoButton(){
        addHousePhotoButton.setOnClickListener { addHousePhotoInRecyclerView() }
    }

    private fun generateHousePhotoList(): ArrayList<HousePhoto>{
        return ArrayList()
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure house photo recyclerview ---------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureHousePhotoRecyclerView(){
        housePhotoRecyclerView.adapter = housePhotoAdapter
        housePhotoRecyclerView.layoutManager = LinearLayoutManager(activity)
        //------------------- Add already selected house photo in recyclerview ---------------------
        fillExistingHousePhotos()
    }

    private fun addHousePhotoInRecyclerView(){

        val housePhotoDescription: String = housePhotoDescriptionEditText.text.toString().trim()
        val newHousePhoto = HousePhoto(null, photoFromStorage.toString(), housePhotoDescription)

        newHousePhotoList.add(newHousePhoto)
        oldHousePhotoList.addAll(newHousePhotoList)
        housePhotoDescriptionInRecyclerView = housePhotoDescription
        housePhotoAdapter.notifyDataSetChanged()
        clearChamps()
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Clear champs -------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clearChamps(){
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
        mainViewModel.deleteHousePhoto(event.housePhoto)
        oldHousePhotoList.remove(event.housePhoto)
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
    //------------------- Fill details house champs ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Fill house photo champs --------------------------------------------------

    private fun fillExistingHousePhotos(){
        val existingHousePhotosList: ArrayList<HousePhoto> = args.currentHouse.housePhotoList!!
        if (existingHousePhotosList.isNotEmpty()) {
            for (e in existingHousePhotosList){
                oldHousePhotoList.add(e)
                housePhotoAdapter.notifyDataSetChanged()
            }
        }
    }

    //------------------- Fill EditTexts champs ----------------------------------------------------

    private fun fillEditTexts(){
        houseDescriptionEditText.setText(args.currentHouse.description)
        houseAddressEditText.setText(args.currentHouse.address)
        housePriceEditText.setText(args.currentHouse.price.toString())
        //upPrice = Integer.parseInt(housePriceEditText.text.toString())
        houseSurfaceEditText.setText(args.currentHouse.surface.toString())
        houseRoomsEditText.setText(args.currentHouse.numberOfRooms.toString())
        houseBathRoomsEditText.setText(args.currentHouse.numberOfBathRooms.toString())
        //------------------- Fill selected poi ----------------------------------------------------
        pointsOfInterests.setText(args.currentHouse.proximityPointsOfInterest.toString())
    }

    //------------------- Show sale date id exists -------------------------------------------------

    private fun showSaleDate(){
        if (args.currentHouse.saleDate != null) {
            //houseSaleDateEditText.setText(Utils.convertUsDateToFrenchDate(args.currentHouse.saleDate!!))
            houseSaleDateEditText.setText(timeConverters.convertLongToTime(args.currentHouse.saleDate!!))
            houseSaleDateLyt.visibility = View.VISIBLE
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- House type spinner ------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun typeSpinner(){
        if (houseTypeSpinner != null){
            houseTypeSpinner.adapter = houseTypeAdapter

            //------------------- Fill house type already selected -------------------------
            fillType()

            houseTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val typeHouseSelected: String = houseTypeSpinner.selectedItem.toString().trim()
                    //upType = typeHouseSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    //------------------- Fill type ----------------------------------------------------------------

    private fun fillType(){
        val houseTypeAlreadySelected: String = args.currentHouse.typeOfHouse.toString().trim()
        val intSelectedType: Int = houseTypeAdapter.getPosition(houseTypeAlreadySelected)
        houseTypeSpinner.setSelection(intSelectedType)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Neighborhood spinner ----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun neighborhoodSpinner(){
        if (neighborhoodSpinner != null){
            neighborhoodSpinner.adapter = neighborhoodAdapter

            //------------------- Fill neighborhood already selected -------------------------------
            fillNeighborhood()

            neighborhoodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val neighborhoodSelected: String = neighborhoodSpinner.selectedItem.toString().trim()
                    //upNeighborhood = neighborhoodSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //------------------- Fill neighborhood --------------------------------------------------------

    private fun fillNeighborhood(){
        val neighborhoodSelected: String = args.currentHouse.neighborhood.toString().trim()
        val intSelectedNeighborhood: Int = neighborhoodAdapter.getPosition(neighborhoodSelected)
        neighborhoodSpinner.setSelection(intSelectedNeighborhood)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Status spinner ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun statusSpinner(){
        if (statusSpinner != null){
            statusSpinner.adapter = statusAdapter

            //------------------- Fill already selected status -------------------------------------
            fillStatus()

            statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val statusSelected: String = statusSpinner.selectedItem.toString().trim()
                    //upStatus = statusSelected

                    if (statusSelected == getString(R.string.sold)){
                        houseSaleDateLyt.visibility = View.VISIBLE
                    }
                    else{
                        houseSaleDateLyt.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //------------------- Fill status --------------------------------------------------------------

    private fun fillStatus(){
        val alreadySelectedStatus: String = args.currentHouse.available.toString().trim()
        val intSelectedStatus: Int = statusAdapter.getPosition(alreadySelectedStatus)
        statusSpinner.setSelection(intSelectedStatus)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set entry date ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun entryDate(){
        val entryDate = args.currentHouse.entryDate
        val entryFrenchDate = timeConverters.convertLongToTime(entryDate)
        houseEntryDate.setText(entryFrenchDate)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set sold date -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun saleDate(){
        houseSaleDateEditText.setOnClickListener { showDatePickerDialog()}
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{ year, month, day -> onDateSelected(year, month, day) }
        datePicker.show(parentFragmentManager, "Date picker")
    }

    @SuppressLint("SetTextI18n")
    private fun onDateSelected(year: Int, month: Int, day: Int){
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
        houseSaleDateEditText.setText("$dayOfWeek/$monthString/$year")
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Points of interest checkbox ---------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clickOnPointsOfInterestsEditText(){
        pointsOfInterests.setOnClickListener {
            pointsOfInterestsCheckBox()
        }
    }

    //-------------------------------- Dialog box multi choices checkbox ---------------------------
    private fun pointsOfInterestsCheckBox(){
        listOfPointsOfInterests = resources.getStringArray(R.array.points_of_interests_array)
        checkedPointsOfInterests = BooleanArray(listOfPointsOfInterests.size)

        pointsOfInterests.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
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
    //-------------------------------- Configure ViewModel -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        injection = Injection()
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Agents spinner ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun agentsSpinner(){

        if (agentsSpinner != null){
            mainViewModel.allAgents.observe(requireActivity(), androidx.lifecycle.Observer { agent ->
                agent.forEach {
                    agentAdapter.add(it)
                }
            })
            agentsSpinner.adapter = agentAdapter

            //------------------- Fill already selected agent --------------------------------------
            fillAgent()

            agentsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    val selectedObject = agentsSpinner.selectedItem as Agent
                    selectedAgentId = selectedObject.id!!
                    //upAgentId = selectedAgentId
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    //------------------- Fill selected agent ------------------------------------------------------

    private fun fillAgent(){
        mainViewModel.allAgents.observe(requireActivity(), androidx.lifecycle.Observer {
            for (a in it) {
                // Depending of house selected agent selected is not the good one if agentId = 2 agent selected = #3, id agentId = 3
                // agent selected = #4 so alreadySelectedIAgentId!!.toInt() - 1
                val alreadySelectedIAgentId = args.currentHouse.agentId
                val agentIdToInt: Int = alreadySelectedIAgentId!!.toInt() - 1
                agentsSpinner.setSelection(agentIdToInt)
            }
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Update house in room db -------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on update button ---------------------------------------------------

    private fun clickOnUpdateHouseButton(){
        updateHouseButton.setOnClickListener {
            saveUpdateHouse()
        }
    }

    //------------------- Update house -------------------------------------------------------------

    private fun saveUpdateHouse(){

        //------------------------------------------------------------------------------------------
        //-------------------------------- Update house --------------------------------------------
        //------------------------------------------------------------------------------------------

        val houseId = args.currentHouse.id
        var housePrice = 0
        var houseSurface = 0
        var houseRooms = 0
        var houseBathRooms = 0
        var houseBedRooms = 0
        var houseDescription = ""
        var houseAddress = ""
        //var longEntryDate = 0

        val typeHouseSelected: String = houseTypeSpinner.selectedItem.toString().trim()

        if (!houseDescriptionEditText.text.isNullOrEmpty()){
            houseDescription = houseDescriptionEditText.text.toString().trim()
        }

        val neighborhoodSelected: String = neighborhoodSpinner.selectedItem.toString().trim()

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
        val pointsOfInterestsSelected = pointsOfInterests.text.toString().trim()
        val entryDate = timeConverters.convertDateToLong(houseEntryDate.text.toString())

        val statusSelectedId: Int = statusSpinner.selectedItemId.toInt()
        val saleDate: String = houseSaleDateEditText.text.toString().trim()
        saleHouseDate = timeConverters.convertDateToLong(saleDate)
        if (statusSelectedId == 1 && saleDate.isEmpty()){
            houseSaleDateEditText.error = "Enter a sale date"
            activity?.showErrorToast("Sale date can't be empty", Toast.LENGTH_SHORT, true)
        }
        else{

            //------------------------------------------------------------------------------------------
            //-------------------------------- Update house photo list ---------------------------------
            //------------------------------------------------------------------------------------------

            for (p in oldHousePhotoList){
                housePhotoIdFromList = p.id
                housePhotoFromList = p.photo.toString()
                housePhotoDescriptionFromList = p.photoDescription
                updateHousePhoto(housePhotoList = HousePhoto(housePhotoIdFromList, housePhotoFromList, housePhotoDescriptionFromList))
            }

            updateHouse(house = House(houseId, oldHousePhotoList, typeHouseSelected, neighborhoodSelected, houseAddress, housePrice,
                    houseSurface, houseRooms, houseBathRooms, houseBedRooms, houseDescription, statusSelected, pointsOfInterestsSelected,
                    entryDate, /*saleHouseDate.toLong()*//*saleDateLong*/saleHouseDate, selectedAgentId))
        }
    }

    private fun updateHouse(house: House){
        mainViewModel.updateHouse(house)
        activity?.showSuccessToast("House updated with success ", Toast.LENGTH_SHORT, true)
        findNavController().navigate(R.id.nav_home)
    }

    private fun updateHousePhoto(housePhotoList: HousePhoto){
        mainViewModel.updateHousePhoto(housePhotoList)
        activity?.showSuccessToast("Size HousePhoto = $housePhotoList", Toast.LENGTH_SHORT, true)
    }
}