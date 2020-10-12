package com.elmanko.mnkworkouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNIT_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)


        setSupportActionBar(toolbar_bmi_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true) // set the back button
            actionBar.title = "Calculate BMI" // set the text on the toolbar

        }

        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView.equals(METRIC_UNITS_VIEW)) {
                if (validateMetricUnits()) {
                    val heightValue: Float = etMetricUnitHeight.text.toString()
                        .toFloat() / 100 // get weight and height in meters from xml
                    val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)

                } else {
                    Toast.makeText(this, "Please enter valid stuff", Toast.LENGTH_SHORT).show()
                }

            } else {
                if (validateUsUnits()) {
                    val usUnitHeightValueFeet: String = etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch: String = etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue: Float = etUsUnitWeight.text.toString().toFloat()

                    val heightValue =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))
                    displayBMIResult(bmi)

                } else {
                    Toast.makeText(this, "Please enter valid stuff", Toast.LENGTH_SHORT).show()
                }
            }
        }




        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener { group, checkedID ->
            if (checkedID == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        tilUsUnitWeight.visibility = View.GONE
        llUsUnitsHeight.visibility = View.GONE

        llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNIT_VIEW
        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        etUsUnitHeightFeet.text!!.clear()
        etMetricUnitHeight.text!!.clear()
        etUsUnitHeightInch.text!!.clear()

        tilUsUnitWeight.visibility = View.VISIBLE
        llUsUnitsHeight.visibility = View.VISIBLE

        llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float) {

        val bmilable: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmilable = " VERY Severely UNDERWEIGHT"
            bmiDescription = "YO, Go eat some STEAK and EGGS and NUTS and EVERYTHING! FOOL!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmilable = "Severely UNDERWEIGHT"
            bmiDescription = "Go eat some STEAK and EGGS and NUTS! FOOL!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmilable = "UNDERWEIGHT"
            bmiDescription = "Go eat some STEAK and EGGS and NUTS! FOOL!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmilable = "NORMAL"
            bmiDescription = "Keep eating what yo'eatin'! FOOL!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmilable = "OVERWEIGHT"
            bmiDescription = "YO, reduce that eatin'! FOOL!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmilable = "MODERATELY OBESE"
            bmiDescription = "Congratulations you are now a FATTY! FOOL!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmilable = "SEVERELY OBESE"
            bmiDescription = "YO, you wanna die? keep eating this much and you will! FOOL!"
        } else {
            bmilable = "VERY SEVERELY OBESE"
            bmiDescription = "Congratulations FATSO you are now a health statistic! FOOL!"
        }

        // make text views visible to display some
        /*tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE*/

        llDisplayBMIResult.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text = bmilable
        tvBMIDescription.text = bmiDescription


    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (etMetricUnitWeight.text.toString().isEmpty())
            isValid = false
        else if (etMetricUnitHeight.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true
        if (etUsUnitHeightFeet.text.toString().isEmpty())
            isValid = false
        else if (etUsUnitHeightInch.text.toString().isEmpty())
            isValid = false
        else if (etUsUnitWeight.text.toString().isEmpty())
            isValid = false

        return isValid

    }

}