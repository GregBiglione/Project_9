package com.openclassrooms.realestatemanager.ui.add_agent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentAdapter
import com.openclassrooms.realestatemanager.model.AgentDataSource

class AddAgentFragment : Fragment() {

    private lateinit var addAgentViewModel: AddAgentViewModel
    private lateinit var agentRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addAgentViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AddAgentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_agent, container, false)
        agentRecyclerView = root.findViewById(R.id.add_agent_recycler_view)
        configureAgentRecyclerView()
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
}