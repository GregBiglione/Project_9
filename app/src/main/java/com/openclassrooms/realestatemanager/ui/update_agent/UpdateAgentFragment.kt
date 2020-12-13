package com.openclassrooms.realestatemanager.ui.update_agent

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import de.hdodenhof.circleimageview.CircleImageView

class UpdateAgentFragment : Fragment() {

    private val args by navArgs<UpdateAgentFragmentArgs>()
    private lateinit var agentUpdatedPhoto: CircleImageView
    private lateinit var agentUpdatedFirstName: TextInputEditText
    private lateinit var agentUpdatedName: TextInputEditText
    private lateinit var agentUpdatedPhone: TextInputEditText
    private lateinit var agentUpdatedEmail: TextInputEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_agent, container, false)
        agentUpdatedPhoto = view.findViewById(R.id.update_agent_photo)
        agentUpdatedFirstName = view.findViewById(R.id.update_agent_first_name)
        agentUpdatedName = view.findViewById(R.id.update_agent_name)
        agentUpdatedPhone = view.findViewById(R.id.update_agent_phone)
        agentUpdatedEmail = view.findViewById(R.id.update_agent_email)
        //autoFillUpdateChamps()
        return view
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Auto fill update champ ---------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun autoFillUpdateChamps(){
        //agentUpdatedPhoto.setImageResource(args.currentAgent.agentPhoto)
        //agentUpdatedPhoto.setImageURI(Uri.parse(args.currentAgent.agentPhoto)) //no
        //agentUpdatedPhoto.setImageResource()
        agentUpdatedFirstName.setText(args.currentAgent.firstName)
        agentUpdatedName.setText(args.currentAgent.name)
        agentUpdatedPhone.setText(args.currentAgent.phoneNumber)
        agentUpdatedEmail.setText(args.currentAgent.email)
    }
}