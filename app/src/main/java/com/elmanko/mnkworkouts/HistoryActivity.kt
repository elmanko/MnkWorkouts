package com.elmanko.mnkworkouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_excersize.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)
        var actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Workout History" // set the text on the toolbar
        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed() //simply go to the previous intent/activity
              // execute the function
        }

        getAllCompletedDates()
    }

    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this,null)
        val allCompletedDatesList = dbHandler.getAllCompletedDatesList() // store the ArrayList returned by getAllCompletedDatesList on a val

        if(allCompletedDatesList.size > 0){
            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE

            tvNoDataAvailable.visibility = View.GONE
            rvHistory.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this, allCompletedDatesList)

            rvHistory.adapter = historyAdapter
        }
        else{
            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE

            tvNoDataAvailable.visibility = View.VISIBLE
        }
        /*
        for (i in allCompletedDatesList){
            Log.i("DateHistoryActivity", "" + i) // write to the log
        }*/

    }
}