package com.openclassrooms.realestatemanager.ui.add_agent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import com.openclassrooms.realestatemanager.events.DeleteAgentEvent
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AddAgentFragment : Fragment() {

    private lateinit var addAgentViewModel: AddAgentViewModel
    private lateinit var agentRecyclerView: RecyclerView
    private lateinit var agentPhoto: CircleImageView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var agentAdapter: AgentAdapter
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
    private lateinit var clearButton: Button

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
        clearButton = root.findViewById(R.id.agent_delete_button)
        configureViewModel()
        configureAgentRecyclerView()
        clickToAddAgentPhoto()
        clickOnAddAgent()
        clickOnClear()
        return root
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure agent recyclerview ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureAgentRecyclerView(){
        agentAdapter = AgentAdapter()
        agentRecyclerView.adapter = agentAdapter
        agentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val agentDao = RealEstateManagerDatabase.getInstance(requireContext()).agentDao
        val propertyDao = RealEstateManagerDatabase.getInstance(requireContext()).houseDao
        val housePhotoDao = RealEstateManagerDatabase.getInstance(requireContext()).housePhotoDao
        val agentRepository = AgentRepository(agentDao)
        val propertyRepository = HouseRepository(propertyDao)
        val housePhotoRepository = HousePhotoRepository(housePhotoDao)
        val factory = ViewModelFactory(agentRepository, propertyRepository, housePhotoRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        //------------------- Get agents from room db ----------------------------------------------
        mainViewModel.allAgents.observe(viewLifecycleOwner, { agent ->
            agentAdapter.setData(agent)
        })
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

    //------------------- Intent to access gallery -------------------------------------------------

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
        val photo: Uri = Uri.parse(agentPhoto.toString())// <-- photo not shown may be Uri
        val firstName: String = agentFirstName.text.toString().trim()
        val name: String = agentName.text.toString().trim()
        val phone: String = "+1 " + agentPhone.text.toString().trim()
        val email: String = agentEmail.text.toString().trim()

        if (inputCheck(firstName, name, phone, email)){
            if(firstName.isEmpty()){
                agentFirstName.error = getString(R.string.enter_first_name)
            }

            if(name.isEmpty()){
                agentName.error = getString(R.string.enter_name)
            }

            if(phone.isEmpty()){
                agentPhone.error = getString(R.string.enter_phone)
            }

            if(email.isEmpty()){
                agentEmail.error = getString(R.string.enter_email)
            }
            else{
                addAgent(agent = Agent(id, photo, firstName, name, phone, email))
                clearChamps()
            }
        }
    }

    private fun addAgent(agent: Agent) {
        mainViewModel.createAgent(agent)
        activity?.showSuccessToast("Welcome " + agent.firstName, Toast.LENGTH_SHORT, true)
        closeKeyboard(agentEmail)

    }

    //----------------------------------------------------------------------------------------------
    //------------------- Remove agent from room db ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Subscribe
    fun onDeleteAgent(event: DeleteAgentEvent) {
        mainViewModel.deleteAgent(event.agent)
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
    //------------------- Clear champs -------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clearChamps(){ //Maybe use TextUtils.isEmpty(firstName) & add parameter to method
        if (agentFirstName.text != null){
            agentFirstName.setText("")
            agentPhoto.setImageResource(R.drawable.ic_baseline_person_24)
        }
        if (agentName.text != null){
            agentName.setText("")
        }
        if (agentPhone.text != null){
            agentPhone.setText("")
        }
        if (agentEmail.text != null){
            agentEmail.setText("")
        }
    }

    //------------------- Click on clear button -------------------------------------------------------

    private fun clickOnClear(){
        clearButton.setOnClickListener {
            clearChamps()
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Check empty champs -------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun inputCheck(firstName: String, name: String, phone: String, email: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(email))
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Hide keyboard ------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun closeKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}