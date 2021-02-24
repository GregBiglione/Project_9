package com.openclassrooms.realestatemanager.adapters

import android.annotation.SuppressLint
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
    //HouseAdapter/*(private var mainActivity: MainActivity, private var currencyBoolean: Boolean)*/
    private var houseList = emptyList<House>()

    //private var mainActivity = MainActivity()
    //private var currencyBoolean = mainActivity.booleanOnCurrencyClick()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.house_item, parent, false)
        return HouseViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
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
        //holder.housePrice.text = "$" + currentHouse.price.toString()

        //if (currencyBoolean ){
        //    //holder.housePrice.text =  "$" + currentHouse.price.toString()
        //    holder.housePrice.text = currentHouse.price?.let { Utils.convertDollarToEuro(it) }.toString() + "€"
        //}
        //else{
        //    //holder.housePrice.text = currentHouse.price?.let { Utils.convertDollarToEuro(it) }.toString() + "€"
        //    holder.housePrice.text =  "$" + currentHouse.price.toString()
        //}

        holder.houseConstraintLayout.setOnClickListener {
            val actionDetail = HomeFragmentDirections.actionNavHomeToDetailedHouseFragment(currentHouse)
            holder.houseConstraintLayout.findNavController().navigate(actionDetail)
        }

        holder.updateHouseButton.setOnClickListener {
            val actionUpdate = HomeFragmentDirections.actionNavHomeToUpdateHouseFragment(currentHouse)
            holder.updateHouseButton.findNavController().navigate(actionUpdate)
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- Data for filter house -----------------------------------
        //------------------------------------------------------------------------------------------

        val agentId: Long = currentHouse.agentId!!
    }

    override fun getItemCount() = houseList.size

    class HouseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val houseConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.house_constraint_lyt)
        val houseImage: ImageView = itemView.findViewById(R.id.house_first_image)
        val houseType: TextView = itemView.findViewById(R.id.house_type)
        val houseNeighborhood: TextView = itemView.findViewById(R.id.house_neighborhood)
        val housePrice: TextView = itemView.findViewById(R.id.house_price)
        val updateHouseButton: ImageView = itemView.findViewById(R.id.house_item_update_button)
    }

    fun setData(house: List<House>){
        this.houseList = house
        notifyDataSetChanged()
    }

    fun filterData(house: List<House>) {
        this.houseList = house // <---
        notifyDataSetChanged()
    }
}