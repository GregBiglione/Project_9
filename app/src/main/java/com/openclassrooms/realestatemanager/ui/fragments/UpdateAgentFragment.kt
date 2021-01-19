package com.openclassrooms.realestatemanager.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.ui.dialog_box.PhotoChoiceDialog
import com.openclassrooms.realestatemanager.utils.ImageConverters
import com.openclassrooms.realestatemanager.utils.SavePhoto
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import de.hdodenhof.circleimageview.CircleImageView

class UpdateAgentFragment : Fragment(), PhotoChoiceDialog.GalleryListener, PhotoChoiceDialog.CameraListener {

    private val args by navArgs<UpdateAgentFragmentArgs>()
    private lateinit var agentUpdatedPhoto: CircleImageView
    private lateinit var agentUpdatedFirstName: TextInputEditText
    private lateinit var agentUpdatedName: TextInputEditText
    private lateinit var agentUpdatedPhone: TextInputEditText
    private lateinit var agentUpdatedEmail: TextInputEditText
    private lateinit var updateButton: Button
    private lateinit var mainViewModel: MainViewModel
    //------------------- Uri to bitmap Conversion -------------------------------------------------
    private lateinit var savePhoto: SavePhoto
    private lateinit var imageConverters: ImageConverters
    private var photoFromStorage: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_agent, container, false)
        agentUpdatedPhoto = view.findViewById(R.id.update_agent_photo)
        agentUpdatedFirstName = view.findViewById(R.id.update_agent_first_name)
        agentUpdatedName = view.findViewById(R.id.update_agent_name)
        agentUpdatedPhone = view.findViewById(R.id.update_agent_phone)
        agentUpdatedEmail = view.findViewById(R.id.update_agent_email)
        updateButton = view.findViewById(R.id.update_agent_update_button)
        configureViewModel()
        imageConverters = ImageConverters()
        savePhoto = SavePhoto()
        autoFillUpdateChamps()
        clickToUpdateAgentPhoto()
        clickOnUpdateAgent()
        return view
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val agentDao = RealEstateManagerDatabase.getInstance(requireContext()).agentDao
        val houseDao = RealEstateManagerDatabase.getInstance(requireContext()).houseDao
        val housePhotoDao = RealEstateManagerDatabase.getInstance(requireContext()).housePhotoDao
        val agentRepository = AgentRepository(agentDao)
        val houseRepository = HouseRepository(houseDao)
        val housePhotoRepository = HousePhotoRepository(housePhotoDao)
        val factory = ViewModelFactory(agentRepository, houseRepository, housePhotoRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Update agent photo from gallery ------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on image view ------------------------------------------------------

    private fun clickToUpdateAgentPhoto(){
        agentUpdatedPhoto.setOnClickListener{ showPhotoChoiceDialogBox() }
    }

    //------------------- Show dialog box ----------------------------------------------------------

    private fun showPhotoChoiceDialogBox(){
        val photoChoiceDialog = PhotoChoiceDialog(this, this)
        //check with parentFragment etc to solve deprecated requireFragmentManager()
        photoChoiceDialog.show(requireFragmentManager(), "Photo choice dialog box")
        photoChoiceDialog.setTargetFragment(this, 1)
    }

    //------------------- Get Uri from dialog box --------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyGalleryPhoto(uriPhoto: Uri?) {
        agentUpdatedPhoto.setImageURI(uriPhoto)
        val bitmap = imageConverters.uriToBitmap(uriPhoto, requireContext())
        val tempUri: Uri? = savePhoto.getImageUri(requireContext(), bitmap)
        photoFromStorage = tempUri
    }

    //------------------- Get Bitmap from dialog box -----------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyCameraPhoto(bitmapPhoto: Bitmap) {
        agentUpdatedPhoto.setImageBitmap(bitmapPhoto)
        val tempUri: Uri? = savePhoto.getImageUri(requireContext(), bitmapPhoto)
        photoFromStorage = tempUri
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Auto fill update champs --------------------------------------------------
    //----------------------------------------------------------------------------------------------

    //@SuppressLint("ResourceAsColor")
    private fun autoFillUpdateChamps(){
        //val photo: String = args.currentAgent.agentPhoto.toString()  // String --> Uri fail
        //agentUpdatedPhoto.setImageURI(Uri.parse(photo))
        agentUpdatedPhoto.setImageURI(args.currentAgent.agentPhoto) // Uri fail
        //agentUpdatedPhoto.setBackgroundColor(R.color.colorAccent) // Color test ok
        agentUpdatedFirstName.setText(args.currentAgent.firstName)
        agentUpdatedName.setText(args.currentAgent.name)
        agentUpdatedPhone.setText(args.currentAgent.phoneNumber)
        agentUpdatedEmail.setText(args.currentAgent.email)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Click on update button ---------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clickOnUpdateAgent(){
        updateButton.setOnClickListener { updateAgent() }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Update agent in room db --------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun updateAgent(){
        val updatedFirstName = agentUpdatedFirstName.text.toString()
        val updatedName = agentUpdatedName.text.toString()
        val updatedPhone = agentUpdatedPhone.text.toString()
        val updatedEmail = agentUpdatedEmail.text.toString()

        if (inputCheck(updatedFirstName, updatedName, updatedPhone, updatedEmail)){
            //------------------- Create updated agent ---------------------------------------------
            val updatedAgent = Agent(args.currentAgent.id, photoFromStorage, updatedFirstName, updatedName, updatedPhone, updatedEmail)
            //------------------- Update agent -----------------------------------------------------
            mainViewModel.updateAgent(updatedAgent)
            activity?.showSuccessToast("Agent updated", Toast.LENGTH_SHORT, true)
            //------------------- back to AddAgentFragment after update ----------------------------
            findNavController().navigate(R.id.nav_add_agent)
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Check empty champs -------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun inputCheck(firstName: String, name: String, phone: String, email: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(email))
    }
}