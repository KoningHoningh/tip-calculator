package com.example.tip_calculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tip_calculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    //calls Binding for later use
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //sets binding to layoutInflater
        setContentView(binding.root) //binds binding to contentView from the Root
        binding.calculateButton.setOnClickListener {
            hideKeyboard(it)
            calculateTip()

        } //calls for the action to happen on clicking the calculate_button
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }


    }

    //The action performed by clicking the button
    private fun calculateTip() {
        val stringInTextField =
            binding.costOfServiceEditText.text.toString() //gets input from the input field
        val cost = stringInTextField.toDoubleOrNull() //converts it to either Null or a Double
        //determines wheter or not cost is Null



        if (cost == null || cost == 0.0) {
            //Sets tip to 0.0
            displayTip(0.0)
            return
        }
        //gets the tipping percentage based on the checked radio button
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPercentage //calculates tip
        //if rounding up was selected, this will round the tip up.
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip * 5) / 5
        }
        //calls displayTip for the actual tip value
        displayTip(tip)
    }

    //formats the view into a currency format and replaces the tip result with the actual tip.
    private fun displayTip(tip: Double) {

        val formattedTip = NumberFormat.getCurrencyInstance(Locale.GERMANY).format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)


    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun hideKeyboard(view: View) {
        view.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
