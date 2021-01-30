package com.openclassrooms.realestatemanager.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class TimeConverters {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateToLong(date: String): Long {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.parse(date).time
    }
}