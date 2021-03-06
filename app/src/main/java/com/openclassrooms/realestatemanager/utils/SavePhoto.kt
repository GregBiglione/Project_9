package com.openclassrooms.realestatemanager.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream


class SavePhoto {

    //----------------------------------------------------------------------------------------------
    //------------------- Get Image from Uri in Media Store ----------------------------------------
    //----------------------------------------------------------------------------------------------

    fun getImageUri(context: Context, bitmapInStorage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        val id: Long = System.currentTimeMillis()
        bitmapInStorage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmapInStorage, "Photo$id", null)
        return Uri.parse(path)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Get path /storage/emulated/0/Pictures/Title.jpg --------------------------
    //----------------------------------------------------------------------------------------------

    fun getRealPathFromUri(context: Context, uri: Uri?): String? {
        val cursor: Cursor? = uri?.let { context?.contentResolver?.query(it, null, null,
                null, null) }
        cursor?.moveToFirst()
        val indexColumn: Int? = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return indexColumn?.let { cursor?.getString(it) }
    }
}