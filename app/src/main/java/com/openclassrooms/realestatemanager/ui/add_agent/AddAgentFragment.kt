package com.openclassrooms.realestatemanager.ui.add_agent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showSuccessToast
import com.droidman.ktoasty.showWarningToast
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.AgentDataSource
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import de.hdodenhof.circleimageview.CircleImageView

class AddAgentFragment : Fragment() {

    private lateinit var addAgentViewModel: AddAgentViewModel
    private lateinit var agentRecyclerView: RecyclerView
    private lateinit var agentPhoto: CircleImageView
    private lateinit var mainViewModel: MainViewModel
    //------------------- Photo from gallery code --------------------------------------------------
    private val IMAGE_PICK_CODE = 2108
    private val IMAGE_PERMISSION_CODE = 1201
    //------------------- Agent input --------------------------------------------------------------
    private lateinit var agentFirstName: TextInputEditText
    private lateinit var agentName: TextInputEditText
    private lateinit var agentPhone: TextInputEditText
    private lateinit var agentEmail: TextInputEditText
    //------------------- Button -------------------------------------------------------------------
    private lateinit var addAgentButton: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addAgentViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AddAgentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_agent, container, false)
        agentRecyclerView = root.findViewById(R.id.add_agent_recycler_view)
        agentPhoto = root.findViewById(R.id.agent_photo)
        agentFirstName = root.findViewById(R.id.agent_first_name)
        agentName = root.findViewById(R.id.agent_name)
        agentPhone = root.findViewById(R.id.agent_phone)
        agentEmail = root.findViewById(R.id.agent_email)
        addAgentButton = root.findViewById(R.id.agent_add_button)
        configureViewModel()
        configureAgentRecyclerView()
        clickToAddAgentPhoto()
        clickOnAddAgent()
        return root
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure agent recyclerview ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureAgentRecyclerView(){
        val listOfAgent = AgentDataSource.createAgentDataSet() //<-- Temporary list with no data from database
        agentRecyclerView.adapter = AgentAdapter(listOfAgent)
        agentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add agent photo from gallery ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on image view ------------------------------------------------------

    private fun clickToAddAgentPhoto(){
        agentPhoto.setOnClickListener{
            checkPermission()
        }
    }

    //------------------- Check permission to access gallery ---------------------------------------

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(context?.applicationContext!!, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                // Permission denied
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                // Show popup permission request
                requestPermissions(permissions, IMAGE_PERMISSION_CODE)
            }
            else{
                // Permission already granted
                pickAgentPhotoFromGallery()
            }
        }
        else{
            // System OS < Marshmallow
            pickAgentPhotoFromGallery()
        }
    }

    //------------------- Handle permission result -------------------------------------------------

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            IMAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission from popup granted
                    pickAgentPhotoFromGallery()
                }
                else{
                    // Permission from popup denied
                    activity?.showWarningToast(getString(R.string.permission_denied), Toast.LENGTH_SHORT, true)
                }
            }
        }
    }

    //------------------- Click on image view ------------------------------------------------------

    private fun pickAgentPhotoFromGallery() {
        val accessGallery = Intent(Intent.ACTION_PICK)
        accessGallery.type ="image/*"
        startActivityForResult(accessGallery, IMAGE_PICK_CODE)
    }

    //------------------- Handle image pick result -------------------------------------------------

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            agentPhoto.setImageURI(data?.data)
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val agentDao = RealEstateManagerDatabase.getInstance(requireContext()).agentDao
        val propertyDao = RealEstateManagerDatabase.getInstance(requireContext()).houseDao
        val agentRepository = AgentRepository(agentDao)
        val propertyRepository = HouseRepository(propertyDao)
        val factory = ViewModelFactory(agentRepository, propertyRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add agent in room db -----------------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on add button ------------------------------------------------------

    private fun clickOnAddAgent(){
        addAgentButton.setOnClickListener {
            saveAgent()
        }
    }

    private fun saveAgent(){
        val id: Long = System.currentTimeMillis()
        val photo: String = agentPhoto.toString()
        val firstName: String = agentFirstName.text.toString().trim()
        val name: String = agentName.text.toString().trim()
        val phone: String = agentPhone.text.toString().trim()
        val email: String = agentEmail.text.toString().trim()

        if(firstName.isEmpty()){
            agentFirstName.error = "Enter a first name"
        }

        if(name.isEmpty()){
            agentName.error = "Enter a name"
        }

        if(phone.isEmpty()){
            agentPhone.error = "Enter a phone number"
        }

        if(email.isEmpty()){
            agentEmail.error = "Enter an email"
        }

        addAgent(agent = Agent(id, photo, firstName, name, phone, email))
    }

    private fun addAgent(agent: Agent) {
        mainViewModel.createAgent(agent)
        activity?.showSuccessToast("Welcome " + agent.firstName, Toast.LENGTH_SHORT, true)

    }
}