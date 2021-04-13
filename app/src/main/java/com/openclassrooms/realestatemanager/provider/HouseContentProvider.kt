package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.House

class HouseContentProvider: ContentProvider(){

    //-------------------------------- For data ----------------------------------------------------

    companion object{
        private const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        private val TABLE_NAME = House::class.java.simpleName
        val URI_HOUSE = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        var houseId: Long? = null
        val cursor: Cursor

        if (context != null){
            try {
               houseId = ContentUris.parseId(uri)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            if(houseId != null){
                cursor = RealEstateManagerDatabase.getInstance(context!!).houseDao.getHouseWithCursor(houseId)
                cursor.setNotificationUri(context!!.contentResolver, uri)
                return cursor
            } else{
                cursor = RealEstateManagerDatabase.getInstance(context!!).houseDao.getAllHousesWithCursor()
                cursor.setNotificationUri(context!!.contentResolver, uri) // uri probably the problem
                return cursor
            }
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.house$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalArgumentException("Failed to insert row for uri $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalArgumentException("Failed to delete row for uri $uri")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalArgumentException("Failed to update row for uri $uri")
    }

}