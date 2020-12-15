package com.openclassrooms.realestatemanager.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.model.HousePhoto

class ListConverters {
    @TypeConverter
    fun convertListToString(photo: String?): ArrayList<HousePhoto> {

        val photoList = object: TypeToken<ArrayList<HousePhoto>>() {}.type
        return Gson().fromJson(photo, photoList)
    }

    @TypeConverter
    fun convertStringToList(photoList: ArrayList<HousePhoto>): String{
        return Gson().toJson(photoList)
    }
}