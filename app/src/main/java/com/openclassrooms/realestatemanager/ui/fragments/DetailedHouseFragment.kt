package com.openclassrooms.realestatemanager.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.droidman.ktoasty.showSuccessToast
import com.droidman.ktoasty.showWarningToast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.utils.TimeConverters
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import de.hdodenhof.circleimageview.CircleImageView


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
    //------------------- Agent item ---------------------------------------------------------------
    private lateinit var agentPhoto: CircleImageView
    private lateinit var agentFirstName: TextView
    private lateinit var agentName: TextView
    private lateinit var agentPhone: TextView
    private lateinit var agentEmail: TextView
    private var agentid: Long? = 0
    private var id: Long? = 0
    private var photo: String = ""
    private var firstName: String = ""
    private var name: String = ""
    private var phone: String = ""
    private var email: String = ""
    //------------------- Main view model ----------------------------------------------------------
    private lateinit var mainViewModel: MainViewModel
    //------------------- Recycler view ------------------------------------------------------------
    private lateinit var agentRecyclerView: RecyclerView
    private lateinit var agentAdapter: AgentAdapter
    private var agentList: List<Agent> = emptyList()
    private lateinit var mainActivity: MainActivity
    private var currencyBoolean: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed_house, container, false)
        setHasOptionsMenu(true)
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
        //------------------- Agent item -----------------------------------------------------------
        agentPhoto = view.findViewById(R.id.detail_agent_item_image)
        agentFirstName = view.findViewById(R.id.detail_agent_item_first_name)
        agentName = view.findViewById(R.id.detail_agent_item_name)
        agentPhone = view.findViewById(R.id.detail_agent_item_phone)
        agentEmail = view.findViewById(R.id.detail_agent_item_email)
        //------------------- Recycler view --------------------------------------------------------
        agentRecyclerView = view.findViewById(R.id.detail_agent_recycler_view)
        mainActivity = MainActivity()
        currencyBoolean = mainActivity.booleanOnCurrencyClick()
        fillCarousel()
        fillDetailHouseChamps()
        showSaleDate()
        configureViewModel()
        configureAgentRecyclerView()
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

    @SuppressLint("SetTextI18n")
    private fun fillDetailHouseChamps(){
        detailDescription.text = args.currentHouse.description
        detailSurface.text = args.currentHouse.surface.toString()
        detailRooms.text = args.currentHouse.numberOfRooms.toString()
        detailBathrooms.text = args.currentHouse.numberOfBathRooms.toString()
        detailBedrooms.text = args.currentHouse.numberOfBedRooms.toString()
        detailNeighborhood.text = args.currentHouse.neighborhood
        detailAddress.text = args.currentHouse.address
        detailPointOfInterests.text = args.currentHouse.proximityPointsOfInterest
        //detailPrice.text = "$" + args.currentHouse.price.toString()
        if (currencyBoolean){
            detailPrice.text = "$" + args.currentHouse.price.toString()
            activity?.showWarningToast("Boolean is $currencyBoolean dans Detailed fragment", Toast.LENGTH_SHORT, true)
        }
        else{
            detailPrice.text = "!!!!!!!!!!!!!!!!!!!!!!!!!!!"
            activity?.showWarningToast("Boolean is $currencyBoolean dans Detailed fragment", Toast.LENGTH_SHORT, true)
        }
        entryDate()
        saleDate()
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
    //-------------------------------- Configure ViewModel -----------------------------------------
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
            agentList = agent
            getAgentInformation()
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Get agent information ---------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun getAgentInformation(){
        agentid = args.currentHouse.agentId
        detailAgent.text = args.currentHouse.agentId.toString()

        for (a in agentList){
            id = a.id

            if (agentid?.let { id?.compareTo(it) } == 0){
                photo = a.agentPhoto.toString()
                firstName = a.firstName
                name = a.name
                phone = a.phoneNumber
                email = a.email

                Glide.with(requireContext())
                        .load(photo)
                        .into(agentPhoto)
                agentFirstName.text = firstName
                agentName.text = name
                agentPhone.text = phone
                agentEmail.text = email
            }
        }
    }

    //------------------- Show sale date if exists -------------------------------------------------

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