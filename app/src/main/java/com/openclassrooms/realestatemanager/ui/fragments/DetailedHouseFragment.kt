package com.openclassrooms.realestatemanager.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentAdapter
import com.openclassrooms.realestatemanager.databinding.FragmentDetailedHouseBinding
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.utils.Constants.Companion.API_KEY
import com.openclassrooms.realestatemanager.utils.TimeConverters
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.MainActivityViewModel
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
    private lateinit var mapLinearLayout: LinearLayout
    //------------------- Time converter -----------------------------------------------------------
    private lateinit var timeConverters: TimeConverters
    //------------------- Image slider -------------------------------------------------------------
    private lateinit var imageSlider: ImageSlider
    private val imageList = ArrayList<SlideModel>()
    //------------------- Static map image view ----------------------------------------------------
    private lateinit var staticMap: ImageView
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
    private lateinit var injection: Injection
    //------------------- Recycler view ------------------------------------------------------------
    private lateinit var agentRecyclerView: RecyclerView
    private lateinit var agentAdapter: AgentAdapter
    private var agentList: List<Agent> = emptyList()
    private lateinit var mainActivity: MainActivity
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var isCurrencyChanged: Boolean = false
    //-------------------------------- XL detail split screen --------------------------------------
    private var xlLandScapeHouseDetail: House? = null
    private lateinit var mapFragment: MapFragment
    private lateinit var homeFragment: HomeFragment


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
        //------------------- Static map image view ------------------------------------------------
        mapLinearLayout = view.findViewById(R.id.detail_map_lyt)
        staticMap = view.findViewById(R.id.detail_static_map)
        //------------------- Recycler view --------------------------------------------------------
        agentRecyclerView = view.findViewById(R.id.detail_agent_recycler_view)
        injection = Injection()
        mainActivity = MainActivity()
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        mapFragment = MapFragment()
        homeFragment = HomeFragment()

        if (activity?.resources?.getBoolean(R.bool.isLandscape) == false){
            fillCarousel()
            fillDetailHouseChamps()
            showSaleDate()
            configureViewModel()
            configureAgentRecyclerView()
            //------------------- Currency view model ----------------------------------------------
            configureMainActivityViewModel()
            displayStaticMap()
            return view
        }
        else{
            when(requireContext().applicationContext/*mainActivity.application*/){ // when(context) else works but map return empty detailedHouse
                mapFragment.context -> {    // when(mainActivity.application) detailedHouse ok but hom crash clicking on itam
                    fillCarousel()
                    fillDetailHouseChamps()
                    showSaleDate()
                    configureViewModel()
                    configureAgentRecyclerView()
                    //------------------- Currency view model --------------------------------------
                    configureMainActivityViewModel()
                    displayStaticMap()
                    return view
                }

                else -> {
                    xlLandScapeHouseDetail = arguments?.getParcelable("DetailSplitScreen")
                    configureMainActivityViewModelSplitScreen()
                    checkScreenSize()
                    return view
                }
            }
            //xlLandScapeHouseDetail = arguments?.getParcelable("DetailSplitScreen")
            //configureMainActivityViewModelSplitScreen()
            //checkScreenSize()
            //return view
            //fillCarousel()
            //fillDetailHouseChamps()
            //showSaleDate()
            //configureViewModel()
            //configureAgentRecyclerView()
            ////------------------- Currency view model --------------------------------------------------
            //configureMainActivityViewModel()
            //displayStaticMap()
            //return view

        //    when(context){
        //        mapFragment.context ->{
        //            fillCarousel()
        //            fillDetailHouseChamps()
        //            showSaleDate()
        //            configureViewModel()
        //            configureAgentRecyclerView()
        //            //------------------- Currency view model --------------------------------------------------
        //            configureMainActivityViewModel()
        //            displayStaticMap()
        //            return view
        //        }
//
        //        //else -> {
        //        //    configureMainActivityViewModelSplitScreen()
        //        //    checkScreenSize()
        //        //    return view
        //        //}
        //    }
        //    // Problem with map XL landscape go to detail
        //    //fillCarousel()
        //    //fillDetailHouseChamps()
        //    //showSaleDate()
        //    //configureViewModel()
        //    //configureAgentRecyclerView()
        //    ////------------------- Currency view model --------------------------------------------------
        //    //configureMainActivityViewModel()
        //    //displayStaticMap()
        //    xlLandScapeHouseDetail = arguments?.getParcelable("DetailSplitScreen")
        //    configureMainActivityViewModelSplitScreen()
        //    checkScreenSize()
        //    return view
        }
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
        switchPrice()
        entryDate()
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure MainActivityViewModel -----------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureMainActivityViewModel(){
        mainActivityViewModel.isClickedCurrency().observe(requireActivity(), Observer {
            isCurrencyChanged = it
            switchPrice()
        })
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Switch price -------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun switchPrice(){

        when(isCurrencyChanged){
            true -> {
                showEurosPrice()
            }
            false -> {
                showDollarsPrice()
            }
        }
    }

    //------------------- Price in € ---------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun showEurosPrice(){
        val euros = args.currentHouse.price?.let { Utils.convertDollarToEuro(it) }
        detailPrice.text = "$euros€"
    }

    //------------------- Price in $ ---------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun showDollarsPrice(){
        val dollars = args.currentHouse.price
        detailPrice.text = "$$dollars"
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
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
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
            saleDate()
            houseSaleDateInputLyt.visibility = View.VISIBLE

        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set entry date ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun entryDate(){
        val entryDate = args.currentHouse.entryDate
        val entryFrenchDate = timeConverters.convertLongToTime(entryDate)
        detailEntryDate.text = " $entryFrenchDate"
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set sold date -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun saleDate(){
        val saleDate = args.currentHouse.saleDate
        if (saleDate != null) {
            val saleFrenchDate = timeConverters.convertLongToTime(saleDate)
            detailSaleDate.text = " $saleFrenchDate"
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Static map --------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun displayStaticMap(){
        val lat = args.currentHouse.lat.toString()
        val lng = args.currentHouse.lng.toString()
        val url = """https://maps.google.com/maps/api/staticmap?center=$lat,$lng&zoom=18&size=300x300&markers=color:red%7Clabel:C%7C$lat,$lng
            &sensor=false&key=$API_KEY"""

        if (lat.toDouble() != 0.0 && lng.toDouble() !=  0.0){
            mapLinearLayout.visibility = View.VISIBLE

            Glide.with(requireContext())
                    .load(url)
                    .into(staticMap)
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- XL landscape screen -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun checkScreenSize() {
        if (xlLandScapeHouseDetail != null) {
            fillCarouselSplitScreen()
            fillDetailHouseChampsSplitScreen()
            configureAgentRecyclerViewSplitScreen()
            configureViewModelSplitScreen()
            showSaleDateSplitScreen()
            displayStaticMapSplitScreen()
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Fill image slider with photos --------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun fillCarouselSplitScreen(){
        if (xlLandScapeHouseDetail?.housePhotoList?.isNotEmpty() == true){
            for (p in xlLandScapeHouseDetail!!.housePhotoList!!){
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
    private fun fillDetailHouseChampsSplitScreen(){
        detailDescription.text = xlLandScapeHouseDetail?.description
        detailSurface.text = xlLandScapeHouseDetail?.surface.toString()
        detailRooms.text = xlLandScapeHouseDetail?.numberOfRooms.toString()
        detailBathrooms.text = xlLandScapeHouseDetail?.numberOfBathRooms.toString()
        detailBedrooms.text = xlLandScapeHouseDetail?.numberOfBedRooms.toString()
        detailNeighborhood.text = xlLandScapeHouseDetail?.neighborhood
        detailAddress.text = xlLandScapeHouseDetail?.address
        detailPointOfInterests.text = xlLandScapeHouseDetail?.proximityPointsOfInterest
        detailPrice.text = xlLandScapeHouseDetail?.price.toString()
        entryDateSplitScreen()
    }

    private fun configureMainActivityViewModelSplitScreen(){
        mainActivityViewModel.isClickedCurrency().observe(requireActivity(), Observer {
            isCurrencyChanged = it
            switchPriceSplitScreen()
        })
    }

    private fun switchPriceSplitScreen(){

        when(isCurrencyChanged){
            true -> {
                showEurosPriceSplitScreen()
            }
            false -> {
                showDollarsPriceSplitScreen()
            }
        }
    }

    //------------------- Price in € ---------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun showEurosPriceSplitScreen(){
        val euros = Utils.convertDollarToEuro(xlLandScapeHouseDetail?.price!!.toInt())
        detailPrice.text = "$euros€"
    }

    //------------------- Price in $ ---------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun showDollarsPriceSplitScreen(){
        val dollars = xlLandScapeHouseDetail?.price
        detailPrice.text = "$$dollars"
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure agent recyclerview ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureAgentRecyclerViewSplitScreen(){
        agentAdapter = AgentAdapter()
        agentRecyclerView.adapter = agentAdapter
        agentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure ViewModel -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModelSplitScreen(){
        val viewModelFactory: ViewModelFactory = injection.provideViewModelFactory(requireContext())
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //------------------- Get agents from room db ----------------------------------------------
        mainViewModel.allAgents.observe(viewLifecycleOwner, { agent ->
            agentAdapter.setData(agent)
            agentList = agent
            getAgentInformationSplitScreen()
        })
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Get agent information ---------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun getAgentInformationSplitScreen(){
        agentid = xlLandScapeHouseDetail?.agentId
        detailAgent.text = xlLandScapeHouseDetail?.agentId.toString()

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

    private fun showSaleDateSplitScreen(){
        if (xlLandScapeHouseDetail?.saleDate != null) {
            saleDateSplitScreen()
            houseSaleDateInputLyt.visibility = View.VISIBLE

        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set entry date ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun entryDateSplitScreen(){
        val entryDate = xlLandScapeHouseDetail?.entryDate
        val entryFrenchDate = entryDate?.let { timeConverters.convertLongToTime(it) }
        detailEntryDate.text = " $entryFrenchDate"
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Set sold date -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun saleDateSplitScreen(){
        val saleDate = xlLandScapeHouseDetail?.saleDate
        if (saleDate != null) {
            val saleFrenchDate = timeConverters.convertLongToTime(saleDate)
            detailSaleDate.text = " $saleFrenchDate"
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Static map --------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun displayStaticMapSplitScreen(){
        val lat = xlLandScapeHouseDetail?.lat.toString()
        val lng = xlLandScapeHouseDetail?.lng.toString()
        val url = """https://maps.google.com/maps/api/staticmap?center=$lat,$lng&zoom=18&size=300x300&markers=color:red%7Clabel:C%7C$lat,$lng
            &sensor=false&key=$API_KEY"""

        if (lat.toDouble() != 0.0 && lng.toDouble() !=  0.0){
            mapLinearLayout.visibility = View.VISIBLE

            Glide.with(requireContext())
                    .load(url)
                    .into(staticMap)
        }
    }

}