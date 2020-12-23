package com.openclassrooms.realestatemanager.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeConverters {

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return simpleDateFormat.parse(date).time
    }
}