package com.elmanko.mnkworkouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        llStart.setOnClickListener{
            //Toast.makeText(this, "Start the excercise", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ExerciseActivity::class.java) // set the next activity to go to "ExerciseActivity"
            startActivity(intent)
        }

        llBMI.setOnClickListener{
            val intent = Intent(this,BMIActivity::class.java) // set the activity to BMIActivity
            startActivity(intent)
        }

        llHistory.setOnClickListener{
            val intent = Intent(this,HistoryActivity::class.java) // set the activity to BMIActivity
            startActivity(intent)
        }

    }
}