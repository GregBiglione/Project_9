package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.TimeConverters
import com.openclassrooms.realestatemanager.utils.Utils


class DetailedHouseFragment : Fragment() {

    private val args by navArgs<DetailedHouseFragmentArgs>()
    private lateinit var detailDescription: TextView
    private lateinit var detailSurface: TextView
    private lateinit var detailRooms: TextView
    private lateinit var detailBathrooms: TextView
    private lateinit var detailBedrooms: TextView
    private lateinit var detailNeighborhood: TextView
    private lateinit var detailAddress: TextView
    private lateinit var detailPointOfInterests: TextView
    private lateinit var detailPrice: TextView
    private lateinit var detailAgent: TextView
    private lateinit var detailEntryDate: TextView
    private lateinit var detailSaleDate: TextView
    //------------------- Text input layout --------------------------------------------------------
    private lateinit var houseSaleDateInputLyt: LinearLayout
    //------------------- Time converter -----------------------------------------------------------
    private lateinit var timeConverters: TimeConverters
    //------------------- Image slider -------------------------------------------------------------
    private lateinit var imageSlider: ImageSlider
    private val imageList = ArrayList<SlideModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed_house, container, false)
        imageSlider = view.findViewById(R.id.detail_slider)
        detailDescription = view.findViewById(R.id.detail_description)
        detailSurface = view.findViewById(R.id.detail_surface)
        detailRooms = view.findViewById(R.id.detail_rooms)
        detailBathrooms = view.findViewById(R.id.detail_bathrooms)
        detailBedrooms = view.findViewById(R.id.detail_bedrooms)
        detailNeighborhood = view.findViewById(R.id.detail_neighborhood)
        detailAddress = view.findViewById(R.id.detail_address)
        detailPointOfInterests = view.findViewById(R.id.detail_points_of_interests)
        detailPrice = view.findViewById(R.id.detail_price)
        detailAgent = view.findViewById(R.id.detail_agent)
        detailEntryDate = view.findViewById(R.id.detail_entry_date)
        houseSaleDateInputLyt = view.findViewById(R.id.detail_sold_lyt)
        detailSaleDate = view.findViewById(R.id.detail_sale_date)
        timeConverters = TimeConverters()
        fillCarousel()
        fillDetailHouseChamps()
        showSaleDate()
        return view
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Fill image slider with photos --------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun fillCarousel(){
        if (args.currentHouse.housePhotoList?.isNotEmpty() == true) {
            for (p in args.currentHouse.housePhotoList!!){
                val photos = p.photo
                val description = p.photoDescription

                imageList.add(SlideModel(photos, description))
                imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
            }
        }
        else{
            imageSlider.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Fill detail house champs -------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun fillDetailHouseChamps(){
        detailDescription.text = args.currentHouse.description
        detailSurface.text = args.currentHouse.surface.toString()
        detailRooms.text = args.currentHouse.numberOfRooms.toString()
        detailBathrooms.text = args.currentHouse.numberOfBathRooms.toString()
        detailBedrooms.text = args.currentHouse.numberOfBedRooms.toString()
        detailNeighborhood.text = args.currentHouse.neighborhood
        detailAddress.text = args.currentHouse.address
        detailPointOfInterests.text = args.currentHouse.proximityPointsOfInterest
        detailPrice.text = args.currentHouse.price.toString()
        detailAgent.text = args.currentHouse.agentId.toString()
        entryDate()
        saleDate()
    }

    //------------------- Show sale date id exists -------------------------------------------------

    private fun showSaleDate(){
        if (args.currentHouse.saleDate != null) {
            houseSaleDateInputLyt.visibility = View.VISIBLE

        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set entry date ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun entryDate(){
        val entryDate = args.currentHouse.entryDate
        val entryFrenchDate = Utils.convertUsDateToFrenchDate(entryDate)
        detailEntryDate.text = entryFrenchDate
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set sold date -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun saleDate(){
        val saleDate = args.currentHouse.saleDate
        if (saleDate != null) {
            val saleFrenchDate = Utils.convertUsDateToFrenchDate(saleDate)
            detailSaleDate.text = saleFrenchDate
        }
    }

}