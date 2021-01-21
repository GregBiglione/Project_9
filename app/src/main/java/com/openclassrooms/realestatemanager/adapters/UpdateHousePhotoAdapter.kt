package com.openclassrooms.realestatemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.events.DeleteHousePhotoEvent
import com.openclassrooms.realestatemanager.model.HousePhoto
import org.greenrobot.eventbus.EventBus

class UpdateHousePhotoAdapter(private var housePhotoList: List<HousePhoto>): RecyclerView.Adapter<UpdateHousePhotoAdapter.HousePhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousePhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.update_house_photo_item, parent, false)
        return HousePhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HousePhotoViewHolder, position: Int) {
        val currentHousePhoto = housePhotoList[position]

        Glide.with(holder.updateAddHousePhoto.context)
                .load(currentHousePhoto.photo)
                .into(holder.updateAddHousePhoto)
        holder.updateAddHousePhotoDescription.text = currentHousePhoto.photoDescription

        holder.updateAddHousePhotoDeleteBtn.setOnClickListener {
            EventBus.getDefault().post(DeleteHousePhotoEvent(currentHousePhoto))
        }
    }

    override fun getItemCount() = housePhotoList.size

    class HousePhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val updateAddHousePhoto: ImageView = itemView.findViewById(R.id.update_add_house_photo_image)
        val updateAddHousePhotoDescription: TextView = itemView.findViewById(R.id.update_add_house_photo_description)
        val updateAddHousePhotoDeleteBtn: ImageButton = itemView.findViewById(R.id.update_add_house_photo_delete_button)
    }
}