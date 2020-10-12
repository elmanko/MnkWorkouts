package com.elmanko.mnkworkouts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbar_finish_activity)

        val actionbar = supportActionBar
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener{
            finish()
        }

        addDateToDatabase()

    }
    private fun addDateToDatabase(){

        val calendar = Calendar.getInstance() // creates a calendar object
        val dateTime = calendar.time // current date and time of the system

        Log.i("DATE:", "" + dateTime)

        val sdf = SimpleDateFormat( "dd MM yyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime) // gets the date, applies a format and store it in a val

        val dbHandler = SqliteOpenHelper(this, null) // create an object of MY defined class SqliteOpenHelper
        dbHandler.addDate(date)
        Log.i("DATE:", "ADDED: " + dateTime)



    }
}