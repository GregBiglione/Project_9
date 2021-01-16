package com.openclassrooms.realestatemanager.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.IOException
import java.io.OutputStream


class SavePhoto {

    @RequiresApi(Build.VERSION_CODES.Q)
    @Throws(IOException::class)
    fun saveBitmap(context: Context, bitmap: Bitmap, format: CompressFormat, mimeType: String, displayName: String){
        val relativeLocation: String = Environment.DIRECTORY_PICTURES
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType + "image/jpg")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)

        val resolver: ContentResolver = context.contentResolver
        var stream: OutputStream? = null
        var uri: Uri? = null
        val quality: Int = 95

        try {
            //------------------- Media creation ---------------------------------------------------
            val contentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, contentValues)
            if (uri == null){
                throw IOException("Failed to create new MediaStore record.")
            }

            //------------------- Output stream creation -------------------------------------------
            stream = resolver.openOutputStream(uri)
            if (stream == null){
                throw IOException("Failed to get output stream.")
            }

            //------------------- Compress bitmap --------------------------------------------------
            if (!bitmap.compress(format, quality, stream)){
                throw IOException("Failed to save bitmap.")
            }
        } catch (e: Exception) {
            if (uri != null){
                //------------------- Remove orphan entry in the media store -----------------------
                resolver.delete(uri, null, null)
            }
            throw e
        } finally {
            stream?.close()
        }
    }
}