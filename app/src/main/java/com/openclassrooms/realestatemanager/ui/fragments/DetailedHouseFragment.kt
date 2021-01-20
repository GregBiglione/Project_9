package com.openclassrooms.realestatemanager.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.TimeConverters
import com.openclassrooms.realestatemanager.utils.UriConverters

class DetailedHouseFragment : Fragment() {

    private val args by navArgs<DetailedHouseFragmentArgs>()
    private lateinit var detailPhoto: ImageView
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
    private lateinit var detailSale: TextView
    //------------------- Invisible layout ---------------------------------------------------------
    private lateinit var detailSoldDateLyt: LinearLayout
    //------------------- Time converter -----------------------------------------------------------
    private lateinit var timeConverters: TimeConverters


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed_house, container, false)
        detailPhoto = view.findViewById(R.id.detail_photo)
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
        detailSale = view.findViewById(R.id.detail_sale_date)
        detailSoldDateLyt = view.findViewById(R.id.detail_sold_lyt)
        timeConverters = TimeConverters()
        fillDetailHouseChamps()
        return view
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Fill detail house champs -------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun fillDetailHouseChamps(){
        if (args.currentHouse.housePhotoList?.isNotEmpty() == true) {
            detailPhoto.setImageURI(Uri.parse(args.currentHouse.housePhotoList!![0].photo))
        }
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
        detailEntryDate.text = args.currentHouse.entryDate?.let { timeConverters.convertLongToTime(it) }
        //if (args.currentHouse.saleDate != null) {
        //    detailSoldDateLyt.isVisible = true
        //    detailSale.text = args.currentHouse.saleDate.toString()
        //}

    }

}