package com.openclassrooms.realestatemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property

class PropertyAdapter(private val propertyList: List<Property>)  : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.property_item, parent,false)
        return PropertyViewHolder(itemView)
    }

    override fun getItemCount() = propertyList.size

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val currentProperty = propertyList[position]

        //------------------- Images ---------------------------------------------------------------
        Glide.with(holder.propertyPhoto1.context)
               .load(currentProperty.photo1)
               .into(holder.propertyPhoto1)
        Glide.with(holder.propertyPhoto2.context)
                .load(currentProperty.photo2)
                .into(holder.propertyPhoto3)
        Glide.with(holder.propertyPhoto3.context)
                .load(currentProperty.photo3)
                .into(holder.propertyPhoto4)
        Glide.with(holder.propertyPhoto4.context)
                .load(currentProperty.photo4)
                .into(holder.propertyPhoto4)
        Glide.with(holder.propertyPhoto5.context)
                .load(currentProperty.photo5)
                .into(holder.propertyPhoto5)
        Glide.with(holder.propertyPhoto6.context)
                .load(currentProperty.photo6)
                .into(holder.propertyPhoto6)
        Glide.with(holder.propertyPhoto7.context)
                .load(currentProperty.photo7)
                .into(holder.propertyPhoto7)
        Glide.with(holder.propertyPhoto8.context)
                .load(currentProperty.photo8)
                .into(holder.propertyPhoto8)
        Glide.with(holder.propertyPhoto9.context)
                .load(currentProperty.photo9)
                .into(holder.propertyPhoto9)
        Glide.with(holder.propertyPhoto10.context)
                .load(currentProperty.photo10)
                .into(holder.propertyPhoto10)

        //------------------- Image description-----------------------------------------------------
        holder.descriptionPhoto1.text = currentProperty.descriptionPhoto1
        holder.descriptionPhoto2.text = currentProperty.descriptionPhoto2
        holder.descriptionPhoto3.text = currentProperty.descriptionPhoto3
        holder.descriptionPhoto4.text = currentProperty.descriptionPhoto4
        holder.descriptionPhoto5.text = currentProperty.descriptionPhoto5
        holder.descriptionPhoto6.text = currentProperty.descriptionPhoto6
        holder.descriptionPhoto7.text = currentProperty.descriptionPhoto7
        holder.descriptionPhoto8.text = currentProperty.descriptionPhoto8
        holder.descriptionPhoto9.text = currentProperty.descriptionPhoto9
        holder.descriptionPhoto10.text = currentProperty.descriptionPhoto10

        //------------------- Type -----------------------------------------------------------------
        holder.propertyType.text = currentProperty.typeOfProperty

        //------------------- Neighborhood ---------------------------------------------------------
        holder.propertyNeighborhood.text = currentProperty.neighborhood

        //------------------- Address --------------------------------------------------------------
        holder.propertyAddress.text = currentProperty.address

        //------------------- Price ----------------------------------------------------------------
        holder.propertyPrice.text = currentProperty.price.toString() //<--- Maybe user price converter here

        //------------------- Surface --------------------------------------------------------------
        holder.propertySurface.text = currentProperty.surface.toString()

        //------------------- Number of rooms ------------------------------------------------------
        holder.propertyNumberOfRooms.text = currentProperty.numberOfRooms.toString()

        //------------------- Number of bathrooms --------------------------------------------------
        holder.propertyNumberOfBathrooms.text = currentProperty.numberOfBathRooms.toString()

        //------------------- Number of bedrooms ---------------------------------------------------
        holder.propertyNumberOfBedrooms.text = currentProperty.numberOfBedRooms.toString()

        //------------------- Description ----------------------------------------------------------
        holder.propertyDescription.text = currentProperty.description

        //------------------- Status ---------------------------------------------------------------
        if (currentProperty.status == true){
            holder.propertyAvailable.text = "Available" //<--Change if/else to Kotlin
        }
        else{
            holder.propertyAvailable.text = "Sold"
        }
        //------------------- Proximity points of interest -----------------------------------------
        holder.propertyProximityPointsOfInterest1.text = currentProperty.proximityPointsOfInterest1
        holder.propertyProximityPointsOfInterest2.text = currentProperty.proximityPointsOfInterest2
        holder.propertyProximityPointsOfInterest3.text = currentProperty.proximityPointsOfInterest3
        holder.propertyProximityPointsOfInterest4.text = currentProperty.proximityPointsOfInterest4
        holder.propertyProximityPointsOfInterest5.text = currentProperty.proximityPointsOfInterest5

        //------------------- Entry date -----------------------------------------------------------
        holder.propertyEntryDate.text = currentProperty.entryDate.toString() //<--- Maybe user date converter here

        //------------------- Sale date ------------------------------------------------------------
        holder.propertySaleDate.text = currentProperty.saleDate.toString()

        //------------------- Agent in charge ------------------------------------------------------
        holder.propertyAgentInCharge.text = currentProperty.agentId.toString()
    }

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //------------------- Images ---------------------------------------------------------------
        var propertyPhoto1: ImageView = itemView.findViewById(R.id.property_first_image)
        var propertyPhoto2: ImageView = itemView.findViewById(R.id.property_second_image)
        var propertyPhoto3: ImageView = itemView.findViewById(R.id.property_third_image)
        var propertyPhoto4: ImageView = itemView.findViewById(R.id.property_fourth_image)
        var propertyPhoto5: ImageView = itemView.findViewById(R.id.property_fifth_image)
        var propertyPhoto6: ImageView = itemView.findViewById(R.id.property_sixth_image)
        var propertyPhoto7: ImageView = itemView.findViewById(R.id.property_seventh_image)
        var propertyPhoto8: ImageView = itemView.findViewById(R.id.property_eighth_image)
        var propertyPhoto9: ImageView = itemView.findViewById(R.id.property_ninth_image)
        var propertyPhoto10: ImageView = itemView.findViewById(R.id.property_tenth_image)
        //------------------- Image description-----------------------------------------------------
        var descriptionPhoto1: TextView = itemView.findViewById(R.id.description_first_image)
        var descriptionPhoto2: TextView = itemView.findViewById(R.id.description_second_image)
        var descriptionPhoto3: TextView = itemView.findViewById(R.id.description_third_image)
        var descriptionPhoto4: TextView = itemView.findViewById(R.id.description_fourth_image)
        var descriptionPhoto5: TextView = itemView.findViewById(R.id.description_fifth_image)
        var descriptionPhoto6: TextView = itemView.findViewById(R.id.description_sixth_image)
        var descriptionPhoto7: TextView = itemView.findViewById(R.id.description_seventh_image)
        var descriptionPhoto8: TextView = itemView.findViewById(R.id.description_eighth_image)
        var descriptionPhoto9: TextView = itemView.findViewById(R.id.description_ninth_image)
        var descriptionPhoto10: TextView = itemView.findViewById(R.id.description_tenth_image)
        //------------------- Type -----------------------------------------------------------------
        var propertyType: TextView = itemView.findViewById(R.id.property_type)
        //------------------- Neighborhood ---------------------------------------------------------
        var propertyNeighborhood: TextView = itemView.findViewById(R.id.property_neighborhood)
        //------------------- Address --------------------------------------------------------------
        var propertyAddress: TextView = itemView.findViewById(R.id.property_address)
        //------------------- Price ----------------------------------------------------------------
        var propertyPrice: TextView = itemView.findViewById(R.id.property_price)
        //------------------- Surface --------------------------------------------------------------
        var propertySurface: TextView = itemView.findViewById(R.id.property_surface)
        //------------------- Number of rooms ------------------------------------------------------
        var propertyNumberOfRooms: TextView = itemView.findViewById(R.id.property_number_of_rooms)
        //------------------- Number of bathrooms --------------------------------------------------
        var propertyNumberOfBathrooms: TextView = itemView.findViewById(R.id.property_number_of_bathrooms)
        //------------------- Number of bedrooms ---------------------------------------------------
        var propertyNumberOfBedrooms: TextView = itemView.findViewById(R.id.property_number_of_bedrooms)
        //------------------- Description ----------------------------------------------------------
        var propertyDescription: TextView = itemView.findViewById(R.id.property_description)
        //------------------- Status ---------------------------------------------------------------
        var propertyAvailable: TextView = itemView.findViewById(R.id.property_available)
        //------------------- Proximity points of interest -----------------------------------------
        var propertyProximityPointsOfInterest1: TextView = itemView.findViewById(R.id.property_point_of_interest_one)
        var propertyProximityPointsOfInterest2: TextView = itemView.findViewById(R.id.property_point_of_interest_two)
        var propertyProximityPointsOfInterest3: TextView = itemView.findViewById(R.id.property_point_of_interest_three)
        var propertyProximityPointsOfInterest4: TextView = itemView.findViewById(R.id.property_point_of_interest_four)
        var propertyProximityPointsOfInterest5: TextView = itemView.findViewById(R.id.property_point_of_interest_five)
        //------------------- Entry date -----------------------------------------------------------
        var propertyEntryDate: TextView = itemView.findViewById(R.id.property_property_entry_date)
        //------------------- Sale date ------------------------------------------------------------
        var propertySaleDate: TextView = itemView.findViewById(R.id.property_sale_date)
        //------------------- Agent in charge ------------------------------------------------------
        var propertyAgentInCharge: TextView = itemView.findViewById(R.id.property_agent_in_charge)
    }
}