package com.openclassrooms.realestatemanager.ui.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.HousePhotoAdapter
import com.openclassrooms.realestatemanager.adapters.UpdateHousePhotoAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.events.DeleteHousePhotoEvent
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.model.HousePhoto
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.ui.dialog_box.PhotoChoiceDialog
import com.openclassrooms.realestatemanager.utils.ImageConverters
import com.openclassrooms.realestatemanager.utils.SavePhoto
import com.openclassrooms.realestatemanager.utils.TimeConverters
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class UpdateHouseFragment : Fragment(), PhotoChoiceDialog.GalleryListener, PhotoChoiceDialog.CameraListener {

    private val args by navArgs<UpdateHouseFragmentArgs>()
    private lateinit var housePhotoRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var housePhotoImageView: ImageView
    private var housePhotoList = generateHousePhotoList()
    private var housePhotoAdapter = UpdateHousePhotoAdapter(housePhotoList)
    private lateinit var housePhotoDescriptionEditText: TextInputEditText
    //------------------- Button -------------------------------------------------------------------
    private lateinit var addHousePhotoButton: Button
    private lateinit var updateHouseButton: Button
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
    private lateinit var houseSaleDateLyt: TextInputLayout
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
    //------------------- Time converter -----------------------------------------------------------
    private lateinit var timeConverters: TimeConverters

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_house, container, false)
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
        houseSaleDateLyt = view.findViewById(R.id.update_house_sale_date_input)
        houseSaleDate = view.findViewById(R.id.update_house_sale_date)
        fillEditTexts()
        //showSaleDate()
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
        photoChoiceDialog.show(requireFragmentManager(), "Photo choice dialog box")
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

        housePhotoList.add(newHousePhoto)
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
    //------------------- Fill details house champs ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Fill house photo champs --------------------------------------------------

    private fun fillExistingHousePhotos(){
        val existingHousePhotosList: ArrayList<HousePhoto> = args.currentHouse.housePhotoList!!
        if (existingHousePhotosList.isNotEmpty()) {
            for (e in existingHousePhotosList){
                housePhotoList.add(e)
                housePhotoAdapter.notifyDataSetChanged()
            }
        }
    }

    //------------------- Fill EditTexts champs ----------------------------------------------------

    private fun fillEditTexts(){
        houseDescriptionEditText.setText(args.currentHouse.description)
        houseAddressEditText.setText(args.currentHouse.address)
        housePriceEditText.setText(args.currentHouse.price.toString())
        houseSurfaceEditText.setText(args.currentHouse.surface.toString())
        houseRoomsEditText.setText(args.currentHouse.numberOfRooms.toString())
        houseBathRoomsEditText.setText(args.currentHouse.numberOfBathRooms.toString())
        houseEntryDate.setText(args.currentHouse.entryDate?.let { timeConverters.convertLongToTime(it) })
        if (args.currentHouse.saleDate != null) {
            houseSaleDate.setText(args.currentHouse.saleDate?.let { timeConverters.convertLongToTime(it) })
        }
    }

    //private fun showSaleDate(){
    //    if (args.currentHouse.saleDate != null) {
    //        houseSaleDate.setText(args.currentHouse.saleDate?.let { timeConverters.convertLongToTime(it) })
    //        houseSaleDateInputLyt.visibility = View.VISIBLE
    //    }
    //}
}