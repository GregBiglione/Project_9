package com.openclassrooms.realestatemanager.ui.add_agent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R

class AddAgentFragment : Fragment() {

    private lateinit var addAgentViewModel: AddAgentViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addAgentViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AddAgentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_agent, container, false)
        return root
    }
}