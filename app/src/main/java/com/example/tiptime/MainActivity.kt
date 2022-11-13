package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateTipAmount.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener{view,keycode,_->handleKeyEvent(view,keycode)}
    }
    private fun calculateTip()
    {
        val stringInTextField=binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if(cost==null)
        {
            displayTip(0.0)
            return
        }
        val tipPercentage=when(binding.tipOptions.checkedRadioButtonId)
        {
            R.id.option_twenty_percent->0.20
            R.id.option_eighteen_percent->0.18
            else->0.15
        }
        var tip=cost*tipPercentage
        if(binding.roundUpTip.isChecked) tip= kotlin.math.ceil(tip)
        displayTip(tip)
    }
    private fun displayTip(tip:Double)
    {
        val formattedTip=NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text=getString(R.string.result_tip_amount,formattedTip)
    }
//    for hiding the keyboard on pressing Enter
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
}