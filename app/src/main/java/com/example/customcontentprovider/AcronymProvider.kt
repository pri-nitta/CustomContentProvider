package com.example.customcontentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class AcronymProvider : ContentProvider() {

    companion object {
        const val PROVIDER_NAME = "com.example.customcontentprovider/AcronymProvider"
        const val URL = " content://$PROVIDER_NAME/ACTABLE"
        val CONTENT_URI = Uri.parse(URL)
        const val _ID = "_id"
        const val NAME = "NAME"
        const val MEANING = "MEANING"
    }

    private lateinit var db: SQLiteDatabase

    override fun onCreate(): Boolean {
        val helper = MyHelper(context)
        db = helper.writableDatabase
        return true
    }

    override fun query(
        uri: Uri,
        collums: Array<out String>?,
        condition: String?,
        condition_value: Array<out String>?,
        order: String?
    ): Cursor? {
        return db.query("ACTABLE", collums, condition, condition_value, null, null, order)
    }

    override fun getType(p0: Uri): String {
        return "vnd.android.cursor.dir/vnd.example.actable"
    }

    override fun insert(uri: Uri, cv: ContentValues?): Uri {
        db.insert("ACTABLE", null, cv)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, condition: String?, condition_val: Array<out String>?): Int {
        val count = db.delete("ACTABLE", condition, condition_val)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        cv: ContentValues?,
        condition: String?,
        condition_values: Array<out String>?
    ): Int {
        val count = db.update("ACTABLE", cv, condition, condition_values)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }
}