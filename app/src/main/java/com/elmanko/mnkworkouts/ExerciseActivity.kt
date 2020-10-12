package com.elmanko.mnkworkouts

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_excersize.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener{

    private var restTimer : CountDownTimer? = null
    private var restProgress : Int = 0
    private var resetTimerDuration : Long = 10

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress : Int = 0
    private var exerciseTimerDuration : Long = 30

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excersize)

        setSupportActionBar(toolbar_excersize_activity)
        var actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_excersize_activity.setNavigationOnClickListener {
            onBackPressed() //simply go to the previous intent/activity
            //customDialogForBackButton()  // execute the function
        }

        tts = TextToSpeech(this,this) // this can be both context and listener
                                                     // as it will be used in THIS activity
                                                     // and ExerciseActivity class is defining
                                                     // a textToSpeech listener on TextToSpeech.OnInitListener
        exerciseList = Constants.defaultExerciseList() // get the list of exercises from our model
        setupRestView()
        setupExerciseStatusRecyclerView()



    }

    override fun onBackPressed() {

        customDialogForBackButton()
        //super.onBackPressed()
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0 // duplicated code as line 13 does it
        }
        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null) {
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun setExerciseProgressBar(){
        progressBarExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000 ,1000) { // set the null object we create in line 10
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = exerciseTimerDuration.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseTimerDuration - exerciseProgress).toString()
            }

            override fun onFinish() {
                //Toast.makeText(this@ExerciseActivity, "Start", Toast.LENGTH_SHORT).show()
                if(currentExercisePosition < exerciseList?.size!! - 1){

                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupRestView()
                }else{
                    //Toast.makeText(this@ExerciseActivity, "Workout Completed", Toast.LENGTH_SHORT).show()
                    finish() // back to homescreen if you press back
                    val intent = Intent(this@ExerciseActivity,FinishActivity :: class.java)
                    startActivity(intent)
                }
            }
        }.start()

    }

    private fun setRestProgressBar(){
        progressBar.progress = restProgress

        restTimer = object : CountDownTimer(resetTimerDuration * 1000,1000) { // set the null object we create in line 10
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged() // refresh the view after changing stuff in model

                setupExerciseView()
                //Toast.makeText(this@ExerciseActivity, "Start", Toast.LENGTH_SHORT).show()
            }
        }.start()
        
    }

    private fun setupExerciseView(){

        llRestView.visibility = View.GONE // diff gone and invisible?
        llExerciseView.visibility = View.VISIBLE

        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        

    }

    private fun setupRestView(){
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false // do not loop the damn sound
            player!!.start()
        }catch (e: Exception){
            e.printStackTrace()
        }


        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }


        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1 ].getName()
        setRestProgressBar()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED ){
                Log.e("TTS","Language not supported")
            }else{
                Log.e("TTS","TTS couldn't be initialized")

            }
        }

    }

    private fun speakOut(text: String){// or a String ;)
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this, // create a new linear layout manager
            LinearLayoutManager.HORIZONTAL, // to be displayed horizontally
            false) // not reversed

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this) // create object from exersiseStatusAdapter and pass the list of exercises

        rvExerciseStatus.adapter = exerciseAdapter // using an adapter for the recycler view
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this) // create a custom dialog object on this activity

        customDialog.setContentView(R.layout.dialog_custom_back_confirmation) // assign a layout to that custom dialog

        customDialog.tvYes.setOnClickListener { // setting actions for the buttons on the custom dialog
            finish() // finish THIS activity, aka the exersise activity
            customDialog.dismiss() // close the custom dialog too
        }
        customDialog.tvNo.setOnClickListener{
            customDialog.dismiss() // only close the custom dialog
        }
        customDialog.show() // showing the custom dialog

    }
}