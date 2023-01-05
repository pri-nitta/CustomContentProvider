package com.example.customcontentprovider

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val helper = MyHelper(applicationContext)
//        val db: SQLiteDatabase = helper.readableDatabase
//        val rs: Cursor = db.rawQuery("SELECT * FROM ACTABLE", null)
//        if (rs.moveToFirst())
//            Toast.makeText(applicationContext, rs.getString(1)+"\n"+rs.getString(2),Toast.LENGTH_LONG).show()
//        rs.close()


       val rs = contentResolver.query(AcronymProvider.CONTENT_URI, arrayOf(AcronymProvider._ID, AcronymProvider.NAME, AcronymProvider.MEANING), null, null, AcronymProvider.NAME)

        findViewById<Button>(R.id.button).setOnClickListener {
            if(rs?.moveToNext() == true){
                findViewById<EditText>(R.id.editTextTextPersonName).setText(rs.getString(1))
                findViewById<EditText>(R.id.editTextTextPersonName2).setText(rs.getString(2))

            }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            if(rs?.moveToPrevious()!!){
                findViewById<EditText>(R.id.editTextTextPersonName).setText(rs.getString(1))
                findViewById<EditText>(R.id.editTextTextPersonName2).setText(rs.getString(2))
            }
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            val cv = ContentValues()
            cv.put(AcronymProvider.NAME, findViewById<EditText>(R.id.editTextTextPersonName).text.toString())
            cv.put(AcronymProvider.MEANING, findViewById<EditText>(R.id.editTextTextPersonName2).text.toString())
            contentResolver.insert(AcronymProvider.CONTENT_URI, cv)
            rs?.requery()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            val cv = ContentValues()
            cv.put(AcronymProvider.MEANING, findViewById<EditText>(R.id.editTextTextPersonName2).text.toString())
            contentResolver.update(AcronymProvider.CONTENT_URI, cv, "NAME = ?", arrayOf(findViewById<EditText>(R.id.editTextTextPersonName).text.toString()))
            rs?.requery()
        }


        findViewById<Button>(R.id.button5).setOnClickListener {
            contentResolver.delete(AcronymProvider.CONTENT_URI, "NAME = ?", arrayOf(findViewById<EditText>(R.id.editTextTextPersonName).text.toString()))
            rs?.requery()
        }

        findViewById<Button>(R.id.button6).setOnClickListener {
            findViewById<EditText>(R.id.editTextTextPersonName).setText("")
            findViewById<EditText>(R.id.editTextTextPersonName2).setText("")
            findViewById<EditText>(R.id.editTextTextPersonName).requestFocus()
        }

        rs?.close()

    }


}