package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

class ImageConverters {

    fun uriToBitmap(uri: Uri?, context: Context): Bitmap{
        //------------------- Convert image to a stream --------------------------------------------
        val inputStream: InputStream? = uri?.let { context.contentResolver.openInputStream(it) }
        //------------------- Open image as bitmap -------------------------------------------------
        return BitmapFactory.decodeStream(inputStream)
    }
}