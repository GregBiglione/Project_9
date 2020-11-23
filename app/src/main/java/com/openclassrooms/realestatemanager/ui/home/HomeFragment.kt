package com.openclassrooms.realestatemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.PropertyAdapter
import com.openclassrooms.realestatemanager.model.Property

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var propertyRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        propertyRecyclerView = root.findViewById(R.id.property_recycler_view)
        return root
    }

    private fun configurePropertyRecyclerView(){
        val listOfProperty = listOf<Property>() //<-- Temporary empty list if not app will crash
        propertyRecyclerView.adapter = PropertyAdapter(listOfProperty)
        propertyRecyclerView.layoutManager = LinearLayoutManager(activity)
        propertyRecyclerView.setHasFixedSize(true)
    }

}