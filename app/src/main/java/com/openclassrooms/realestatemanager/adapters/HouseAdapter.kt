package com.openclassrooms.realestatemanager.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.events.NavigateToDetailedHouseInSameFragmentEvent
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.ui.fragments.HomeFragmentDirections
import com.openclassrooms.realestatemanager.utils.Utils
import org.greenrobot.eventbus.EventBus

class HouseAdapter(private var isCurrencyChanged: Boolean): RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {
    private var houseList = emptyList<House>()
    private lateinit var context: Context
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.house_item, parent, false)
        context = parent.context
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

        when(isCurrencyChanged){
            true -> {
                val dollarsPrice = currentHouse.price
                var eurosPrice = Utils.convertDollarToEuro(dollarsPrice!!.toInt())
                holder.housePrice.text = "$eurosPrice€"
            }
            false -> {
                holder.housePrice.text = "$" + currentHouse.price
            }
        }

        holder.houseConstraintLayout.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()

            if (!context.resources.getBoolean(R.bool.isLandscape)){
                val actionDetail = HomeFragmentDirections.actionNavHomeToDetailedHouseFragment(currentHouse)
                holder.houseConstraintLayout.findNavController().navigate(actionDetail)
            }
            else{
                EventBus.getDefault().post(NavigateToDetailedHouseInSameFragmentEvent(currentHouse))
            }
        }

        if (selectedPosition == position) {
            holder.houseConstraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            holder.houseNeighborhood.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.housePrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccentLight))
        } else {
            holder.houseConstraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            holder.houseNeighborhood.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            holder.housePrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        }

        holder.updateHouseButton.setOnClickListener {
            val actionUpdate = HomeFragmentDirections.actionNavHomeToUpdateHouseFragment(currentHouse)
            holder.updateHouseButton.findNavController().navigate(actionUpdate)
        }
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
}