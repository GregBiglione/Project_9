package com.openclassrooms.realestatemanager.ui.update_agent

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.ui.add_agent.AddAgentFragment
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import de.hdodenhof.circleimageview.CircleImageView

class UpdateAgentFragment : Fragment() {

    private val args by navArgs<UpdateAgentFragmentArgs>()
    private lateinit var agentUpdatedPhoto: CircleImageView
    private lateinit var agentUpdatedFirstName: TextInputEditText
    private lateinit var agentUpdatedName: TextInputEditText
    private lateinit var agentUpdatedPhone: TextInputEditText
    private lateinit var agentUpdatedEmail: TextInputEditText
    private lateinit var updateButton: Button
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_agent, container, false)
        agentUpdatedPhoto = view.findViewById(R.id.update_agent_photo)
        agentUpdatedFirstName = view.findViewById(R.id.update_agent_first_name)
        agentUpdatedName = view.findViewById(R.id.update_agent_name)
        agentUpdatedPhone = view.findViewById(R.id.update_agent_phone)
        agentUpdatedEmail = view.findViewById(R.id.update_agent_email)
        autoFillUpdateChamps()
        configureViewModel()
        clickOnUpdateAgent()
        return view
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val agentDao = RealEstateManagerDatabase.getInstance(requireContext()).agentDao
        val houseDao = RealEstateManagerDatabase.getInstance(requireContext()).houseDao
        val agentRepository = AgentRepository(agentDao)
        val houseRepository = HouseRepository(houseDao)
        val factory = ViewModelFactory(agentRepository, houseRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Auto fill update champ ---------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun autoFillUpdateChamps(){
        //agentUpdatedPhoto.setImageResource(args.currentAgent.agentPhoto)
        agentUpdatedPhoto.setImageURI(args.currentAgent.agentPhoto) //no
        //agentUpdatedPhoto.setImageResource()
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
        val updatedPhoto = Uri.parse(agentUpdatedPhoto.toString())
        val updatedFirstName = agentUpdatedFirstName.text.toString()
        val updatedName = agentUpdatedName.text.toString()
        val updatedPhone = agentUpdatedPhone.text.toString()
        val updatedEmail = agentUpdatedEmail.text.toString()

        if (inputCheck(updatedFirstName, updatedName, updatedPhone, updatedEmail)){
            //------------------- Create updated agent ---------------------------------------------
            val updatedAgent = Agent(args.currentAgent.id, updatedPhoto, updatedFirstName, updatedName, updatedPhone, updatedEmail)
            //------------------- Update agent -----------------------------------------------------
            mainViewModel.updateAgent(updatedAgent)
            activity?.showSuccessToast("Agent $updatedFirstName updated", Toast.LENGTH_SHORT, true)
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