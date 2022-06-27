package com.example.tip_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tip_calculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    //calls Binding for later use
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //sets binding to layoutInflater
        setContentView(binding.root) //binds binding to contentView from the Root
        binding.calculateButton.setOnClickListener{ calculateTip()} //calls for the action to happen on clicking the calculate_button
    }

    //The action performed by clicking the button
    private fun calculateTip() {
        val stringInTextField = binding.plainTextInput.text.toString() //gets input from the input field
        val cost = stringInTextField.toDoubleOrNull() //converts it to either Null or a Double
        //determines wheter or not cost is Null and ends the calculation with an empty string if Null
        if (cost == null)
        {
            binding.tipResult.text = ""
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
        if (binding.roundUpSwitch.isChecked) { tip = ceil(tip)
        }

        //formats the view into a currency format and replaces the tip result with the actual tip.
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}
