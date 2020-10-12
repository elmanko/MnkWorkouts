package com.elmanko.mnkworkouts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory:  SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context , DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        private val DATABASE_VERSION = 1 // version of this DB
        private val DATABASE_NAME = "MnkWorkouts.db" // name of the DB
        private val TABLE_HISTORY = "history" // name of the Table
        private val COLUMN_ID = "_id" // column _id
        private val COLUMN_COMPLETED_DATE = "completed_date" // column completed_date

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID + "INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE + " TEXT)")
        // CREATE TABLE history ( _id INTEGER PRIMARY KEY, completed_date TEXT)
        db?.execSQL(CREATE_EXERCISE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS" + TABLE_HISTORY) // drop and recreate with latest DATABASE_VERSION after altering DDL
        onCreate(db)

    }

    fun addDate(date : String){
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date) // store the date in the column

        val db = this.writableDatabase
        db.insert(TABLE_HISTORY,null,values) // insert requires the values as an object
        db.close()
    }

    fun getAllCompletedDatesList() : ArrayList<String>{

        val list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null ) // full table scan :( DBA heartbrake

        while (cursor.moveToNext()){
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))) // retrieve the value with the cursor
            list.add(dateValue) // add the value to our ArrayList
        }
        cursor.close() // closes the connection with the cursor
        return  list // the function has to return the ArrayList with Strings
    }


}