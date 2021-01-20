package com.openclassrooms.realestatemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.ui.fragments.HomeFragmentDirections

class HouseAdapter: RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    private var houseList = emptyList<House>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.house_item, parent, false)
        return HouseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val currentHouse = houseList[position]

        if (!currentHouse.housePhotoList?.isEmpty()!!) {
            Glide.with(holder.houseImage.context)
                    .load(currentHouse.housePhotoList!![0].photo)
                    .into(holder.houseImage)
        }
        else{
            Glide.with(holder.houseImage.context)
                    .load(R.drawable.ic_baseline_house_24)
                    .into(holder.houseImage)
        }
        holder.houseType.text = currentHouse.typeOfHouse
        holder.houseNeighborhood.text = currentHouse.neighborhood
        holder.housePrice.text = currentHouse.price.toString()

        holder.houseConstraintLayout.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToDetailedHouseFragment(currentHouse)
            holder.houseConstraintLayout.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = houseList.size

    class HouseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val houseConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.house_constraint_lyt)
        val houseImage: ImageView = itemView.findViewById(R.id.house_first_image)
        val houseType: TextView = itemView.findViewById(R.id.house_type)
        val houseNeighborhood: TextView = itemView.findViewById(R.id.house_neighborhood)
        val housePrice: TextView = itemView.findViewById(R.id.house_price)
    }

    fun setData(house: List<House>){
        this.houseList = house
        notifyDataSetChanged()
    }
}
//class HouseAdapter: RecyclerView.Adapter<HouseAdapter.HouseViewHolder>(){
//
//    private var houseList = emptyList<House>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//                .inflate(R.layout.house_item, parent,false)
//        return HouseViewHolder(itemView)
//    }
//
//    override fun getItemCount() = houseList.size
//
//    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
//        val currentHouse = houseList[position]
//
//        //------------------- Images ---------------------------------------------------------------
//        if (!currentHouse.housePhotoList?.isEmpty()!!) {
//            Glide.with(holder.housePhoto1.context)
//                   .load(currentHouse.housePhotoList!![0].photo) // <-- get 1st item
//                   .into(holder.housePhoto1)
//            holder.descriptionPhoto1.text = currentHouse.housePhotoList!![0].photoDescription
//            Glide.with(holder.housePhoto2.context)
//                    .load(currentHouse.housePhotoList!![1].photo)
//                    .into(holder.housePhoto3)
//           //Glide.with(holder.housePhoto3.context)
//           //        .load(currentHouse.housePhotoList!![2].photo)
//           //        .into(holder.housePhoto4)
//           //Glide.with(holder.housePhoto4.context)
//           //        .load(currentHouse.housePhotoList!![3].photo)
//           //        .into(holder.housePhoto4)
//           //Glide.with(holder.housePhoto5.context)
//           //        .load(currentHouse.housePhotoList!![4].photo)
//           //        .into(holder.housePhoto5)
//           //Glide.with(holder.housePhoto6.context)
//           //        .load(currentHouse.housePhotoList!![5].photo)
//           //        .into(holder.housePhoto6)
//           //Glide.with(holder.housePhoto7.context)
//           //        .load(currentHouse.housePhotoList!![6].photo)
//           //        .into(holder.housePhoto7)
//           //Glide.with(holder.housePhoto8.context)
//           //        .load(currentHouse.housePhotoList!![7].photo)
//           //        .into(holder.housePhoto8)
//           //Glide.with(holder.housePhoto9.context)
//           //        .load(currentHouse.housePhotoList!![8].photo)
//           //        .into(holder.housePhoto9)
//           //Glide.with(holder.housePhoto10.context)
//           //        .load(currentHouse.housePhotoList!![9].photo)
//           //        .into(holder.housePhoto10)
//        } else {
//            Glide.with(holder.housePhoto1.context)
//                    .load(R.drawable.ic_baseline_photo_48)
//                    .into(holder.housePhoto1)
//        }
//
//        //------------------- Image description-----------------------------------------------------
//        //holder.descriptionPhoto1.text = currentHouse.housePhotoList!![0].photoDescription
//        //holder.descriptionPhoto2.text = currentHouse.housePhotoList!![1].photoDescription
//        //holder.descriptionPhoto3.text = currentHouse.housePhotoList!![2].photoDescription
//        //holder.descriptionPhoto4.text = currentHouse.housePhotoList!![3].photoDescription
//        //holder.descriptionPhoto5.text = currentHouse.housePhotoList!![4].photoDescription
//        //holder.descriptionPhoto6.text = currentHouse.housePhotoList!![5].photoDescription
//        //holder.descriptionPhoto7.text = currentHouse.housePhotoList!![6].photoDescription
//        //holder.descriptionPhoto8.text = currentHouse.housePhotoList!![7].photoDescription
//        //holder.descriptionPhoto9.text = currentHouse.housePhotoList!![8].photoDescription
//        //holder.descriptionPhoto10.text = currentHouse.housePhotoList!![9].photoDescription
//
//        //------------------- Type -----------------------------------------------------------------
//        holder.houseType.text = currentHouse.typeOfHouse
//
//        //------------------- Neighborhood ---------------------------------------------------------
//        holder.houseNeighborhood.text = currentHouse.neighborhood
//
//        //------------------- Address --------------------------------------------------------------
//        holder.houseAddress.text = currentHouse.address
//
//        //------------------- Price ----------------------------------------------------------------
//        holder.housePrice.text = currentHouse.price.toString() //<--- Maybe user price converter here
//
//        //------------------- Surface --------------------------------------------------------------
//        holder.houseSurface.text = currentHouse.surface.toString()
//
//        //------------------- Number of rooms ------------------------------------------------------
//        holder.houseNumberOfRooms.text = currentHouse.numberOfRooms.toString()
//
//        //------------------- Number of bathrooms --------------------------------------------------
//        holder.houseNumberOfBathrooms.text = currentHouse.numberOfBathRooms.toString()
//
//        //------------------- Number of bedrooms ---------------------------------------------------
//        holder.houseNumberOfBedrooms.text = currentHouse.numberOfBedRooms.toString()
//
//        //------------------- Description ----------------------------------------------------------
//        holder.houseDescription.text = currentHouse.description
//
//        //------------------- Available ------------------------------------------------------------
//        holder.houseAvailable.text = currentHouse.available
//
//        //------------------- Proximity points of interest -----------------------------------------
//        holder.houseProximityPointsOfInterest.text = currentHouse.proximityPointsOfInterest
//        //holder.houseProximityPointsOfInterest2.text = currentHouse.proximityPointsOfInterest2
//        //holder.houseProximityPointsOfInterest3.text = currentHouse.proximityPointsOfInterest3
//        //holder.houseProximityPointsOfInterest4.text = currentHouse.proximityPointsOfInterest4
//        //holder.houseProximityPointsOfInterest5.text = currentHouse.proximityPointsOfInterest5
//
//        //------------------- Entry date -----------------------------------------------------------
//        holder.houseEntryDate.text = currentHouse.entryDate.toString() //<--- Maybe user date converter here
//
//        //------------------- Sale date ------------------------------------------------------------
//        holder.houseSaleDate.text = currentHouse.saleDate.toString()
//
//        //------------------- Agent in charge ------------------------------------------------------
//        holder.houseAgentInCharge.text = currentHouse.agentId.toString()
//    }
//
//    class HouseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        //------------------- Images ---------------------------------------------------------------
//        var housePhoto1: ImageView = itemView.findViewById(R.id.house_first_image)
//        var housePhoto2: ImageView = itemView.findViewById(R.id.house_second_image)
//        var housePhoto3: ImageView = itemView.findViewById(R.id.house_third_image)
//        var housePhoto4: ImageView = itemView.findViewById(R.id.house_fourth_image)
//        var housePhoto5: ImageView = itemView.findViewById(R.id.house_fifth_image)
//        var housePhoto6: ImageView = itemView.findViewById(R.id.house_sixth_image)
//        var housePhoto7: ImageView = itemView.findViewById(R.id.house_seventh_image)
//        var housePhoto8: ImageView = itemView.findViewById(R.id.house_eighth_image)
//        var housePhoto9: ImageView = itemView.findViewById(R.id.house_ninth_image)
//        var housePhoto10: ImageView = itemView.findViewById(R.id.house_tenth_image)
//        //------------------- Image description-----------------------------------------------------
//        var descriptionPhoto1: TextView = itemView.findViewById(R.id.description_first_image)
//        var descriptionPhoto2: TextView = itemView.findViewById(R.id.description_second_image)
//        var descriptionPhoto3: TextView = itemView.findViewById(R.id.description_third_image)
//        var descriptionPhoto4: TextView = itemView.findViewById(R.id.description_fourth_image)
//        var descriptionPhoto5: TextView = itemView.findViewById(R.id.description_fifth_image)
//        var descriptionPhoto6: TextView = itemView.findViewById(R.id.description_sixth_image)
//        var descriptionPhoto7: TextView = itemView.findViewById(R.id.description_seventh_image)
//        var descriptionPhoto8: TextView = itemView.findViewById(R.id.description_eighth_image)
//        var descriptionPhoto9: TextView = itemView.findViewById(R.id.description_ninth_image)
//        var descriptionPhoto10: TextView = itemView.findViewById(R.id.description_tenth_image)
//        //------------------- Type -----------------------------------------------------------------
//        var houseType: TextView = itemView.findViewById(R.id.house_type)
//        //------------------- Neighborhood ---------------------------------------------------------
//        var houseNeighborhood: TextView = itemView.findViewById(R.id.house_neighborhood)
//        //------------------- Address --------------------------------------------------------------
//        var houseAddress: TextView = itemView.findViewById(R.id.house_address)
//        //------------------- Price ----------------------------------------------------------------
//        var housePrice: TextView = itemView.findViewById(R.id.house_price)
//        //------------------- Surface --------------------------------------------------------------
//        var houseSurface: TextView = itemView.findViewById(R.id.house_surface)
//        //------------------- Number of rooms ------------------------------------------------------
//        var houseNumberOfRooms: TextView = itemView.findViewById(R.id.house_number_of_rooms)
//        //------------------- Number of bathrooms --------------------------------------------------
//        var houseNumberOfBathrooms: TextView = itemView.findViewById(R.id.house_number_of_bathrooms)
//        //------------------- Number of bedrooms ---------------------------------------------------
//        var houseNumberOfBedrooms: TextView = itemView.findViewById(R.id.house_number_of_bedrooms)
//        //------------------- Description ----------------------------------------------------------
//        var houseDescription: TextView = itemView.findViewById(R.id.house_description)
//        //------------------- Status ---------------------------------------------------------------
//        var houseAvailable: TextView = itemView.findViewById(R.id.house_available)
//        //------------------- Proximity points of interest -----------------------------------------
//        var houseProximityPointsOfInterest: TextView = itemView.findViewById(R.id.house_point_of_interest_one)
//        //var houseProximityPointsOfInterest2: TextView = itemView.findViewById(R.id.house_point_of_interest_two)
//        //var houseProximityPointsOfInterest3: TextView = itemView.findViewById(R.id.house_point_of_interest_three)
//        //var houseProximityPointsOfInterest4: TextView = itemView.findViewById(R.id.house_point_of_interest_four)
//        //var houseProximityPointsOfInterest5: TextView = itemView.findViewById(R.id.house_point_of_interest_five)
//        //------------------- Entry date -----------------------------------------------------------
//        var houseEntryDate: TextView = itemView.findViewById(R.id.house_entry_date)
//        //------------------- Sale date ------------------------------------------------------------
//        var houseSaleDate: TextView = itemView.findViewById(R.id.house_sale_date)
//        //------------------- Agent in charge ------------------------------------------------------
//        var houseAgentInCharge: TextView = itemView.findViewById(R.id.house_agent_in_charge)
//    }
//
//    fun setData(house: List<House>){
//        this.houseList = house
//        notifyDataSetChanged()
//    }
//}