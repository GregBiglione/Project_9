package com.openclassrooms.realestatemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.HousePhoto

class HousePhotoAdapter: RecyclerView.Adapter<HousePhotoAdapter.HousePhotoViewHolder>() {

    private var housePhotoList = emptyList<HousePhoto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousePhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.house_photo_item, parent, false)
        return HousePhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HousePhotoViewHolder, position: Int) {
        val currentHousePhoto = housePhotoList[position]

        Glide.with(holder.addHousePhoto.context)
                .load(currentHousePhoto.photo)
                .into(holder.addHousePhoto)
        holder.addHousePhotoDescription.text = currentHousePhoto.photoDescription
    }

    override fun getItemCount() = housePhotoList.size

    class HousePhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val addHousePhoto: ImageView = itemView.findViewById(R.id.add_house_photo_image)
        val addHousePhotoDescription: TextView = itemView.findViewById(R.id.add_house_photo_description)
        val addHousePhotoUpdateBtn: Button = itemView.findViewById(R.id.add_house_photo_update_button)
        val addHousePhotoDeleteBtn: Button = itemView.findViewById(R.id.add_house_photo_delete_button)
    }

    //fun setData(housePhoto: List<HousePhoto>){
    //    this.housePhotoList = housePhoto
    //    notifyDataSetChanged()
    //}
}