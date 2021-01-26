package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.model.House


class HouseContentProvider: ContentProvider() {

    //-------------------------------- For data ---------(------------------------------------------

    companion object{
        private const val AUTHORITY = "com.openclassrooms.savemytrip.provider"
        private val TABLE_NAME = House::class.java.simpleName
        private val URI_HOUSE = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        if (context != null) {
            val houseId = ContentUris.parseId(uri)
            val cursor: Cursor? = RealEstateManagerDatabase.getInstance(context!!).houseDao.getHouseWithCursor(houseId)
            cursor?.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.house/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        //if (context != null){
        //    val id: Long = RealEstateManagerDatabase.getInstance(context).houseDao.createHouse(House().fromContentValues(values))
        //    context!!.contentResolver.notifyChange(uri, null)
        //    return ContentUris.withAppendedId(uri, id)
        //}
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
        //if (context != null){
        //    val count: Int = RealEstateManagerDatabase.getInstance(context).houseDao.updateHouse(House().fromContentValues(values))
        //    context!!.contentResolver.notifyChange(uri, null)
        //    return count
        //}
        //throw IllegalArgumentException("Failed to update row into $uri");
    }
}