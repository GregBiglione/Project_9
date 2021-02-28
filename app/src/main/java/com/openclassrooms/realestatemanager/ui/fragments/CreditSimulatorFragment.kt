package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import java.text.DecimalFormat

class CreditSimulatorFragment: Fragment() {

    //------------------- Edit text ----------------------------------------------------------------
    private lateinit var priceEt: TextInputEditText
    private lateinit var contributionEt: TextInputEditText
    private lateinit var borrowedAmountEt: TextInputEditText
    private lateinit var rateEt: TextInputEditText
    private lateinit var numberOfMonthsEt: TextInputEditText
    private lateinit var monthlyPaymentEt: TextInputEditText
    private lateinit var totalCostEt: TextInputEditText
    private lateinit var totalInterestsEt: TextInputEditText
    //------------------- Button -------------------------------------------------------------------
    private lateinit var simulateButton: Button
    private lateinit var clearButton: Button

    private var price: Double = 0.0
    private var contribution: Double? = 0.0
    private var priceWithContribution: Double = 0.0
    private var totalCost: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_credit_simulator, container, false)
        priceEt = root.findViewById(R.id.simulator_house_price_et)
        contributionEt = root.findViewById(R.id.simulator_contribution_et)
        borrowedAmountEt = root.findViewById(R.id.simulator_borrowed_amount_et)
        rateEt = root.findViewById(R.id.simulator_rate_et)
        numberOfMonthsEt = root.findViewById(R.id.simulator_month_et)
        monthlyPaymentEt = root.findViewById(R.id.simulator_monthly_payment_et)
        totalCostEt = root.findViewById(R.id.simulator_total_cost_et)
        totalInterestsEt = root.findViewById(R.id.simulator_total_interests_et)
        simulateButton = root.findViewById(R.id.simulate_button)
        clearButton = root.findViewById(R.id.simulate_clear_button)
        clickOnSimulateButton()
        clickOnClear()
        return root
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Calculate cost with or without contribution -----------------
    //----------------------------------------------------------------------------------------------

    private fun calculatePriceWithContribution(){
        var priceSimulation = 0.0
        var contributionSimulation = 0.0
        var priceWithContributionSimulation = 0.0

        if (priceEt.text.isNullOrEmpty()){
            priceEt.error = getString(R.string.simulator_error_price)
        }

        if (!priceEt.text.isNullOrEmpty() && !contributionEt.text.isNullOrEmpty()){
            priceSimulation = priceEt.text.toString().toDouble()
            contributionSimulation = contributionEt.text.toString().toDouble()
            priceWithContributionSimulation = priceSimulation - contributionSimulation
            price = priceSimulation
            contribution = contributionSimulation
            priceWithContribution = priceWithContributionSimulation
            borrowedAmountEt.setText(priceWithContribution.toString())
        }
        else if (!priceEt.text.isNullOrEmpty() && contributionEt.text.isNullOrEmpty()){
            priceWithContribution = price
            borrowedAmountEt.setText(priceWithContribution.toString())
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Calculate total cost ----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun calculateTotalCost(){
        var rate = 0.0
        var totalCostSimulation = 0.0

        if (rateEt.text.isNullOrEmpty()){
            rateEt.error = getString(R.string.simulator_error_rate)
        }
        else{
            rate = rateEt.text.toString().toDouble()
            price = borrowedAmountEt.text.toString().toDouble()
            totalCostSimulation = price + (price * (rate / 100))
            totalCost = totalCostSimulation
            val df = DecimalFormat("#.##")
            val roundTotalCost: String = df.format(totalCost)
            totalCostEt.setText(roundTotalCost)
            closeKeyboard(numberOfMonthsEt)
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Calculate interests -----------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun calculateInterests(){
        var totalInterests = 0.0
        totalInterests = totalCost - price
        val df = DecimalFormat("#.##")
        val roundTotalInterests: String = df.format(totalInterests)
        totalInterestsEt.setText(roundTotalInterests)

    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Calculate monthly payment -----------------------------------
    //----------------------------------------------------------------------------------------------

    private fun calculateMonthlyPayment(){
        var numberOfMonths = 0
        var monthlyPayment: Double = 0.0

        if (numberOfMonthsEt.text.isNullOrEmpty()){
            numberOfMonthsEt.error = getString(R.string.simulator_error_months)
        }
        else{
            numberOfMonths = numberOfMonthsEt.text.toString().toInt()
            monthlyPayment = totalCost / numberOfMonths
            val df = DecimalFormat("#.##")
            val roundMonthlyPayment: String = df.format(monthlyPayment)
            monthlyPaymentEt.setText(roundMonthlyPayment)
            closeKeyboard(numberOfMonthsEt)
        }
    }

    private fun clickOnSimulateButton(){
        simulateButton.setOnClickListener {
            calculatePriceWithContribution()
            calculateTotalCost()
            calculateInterests()
            calculateMonthlyPayment()
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Clear champs ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clearChamps(){
        if (priceEt.text != null){
            priceEt.setText("")
        }
        if (contributionEt.text != null){
            contributionEt.setText("")
        }
        if (borrowedAmountEt.text != null){
            borrowedAmountEt.setText("")
        }
        if (rateEt.text != null){
            rateEt.setText("")
        }

        if (numberOfMonthsEt.text != null){
            numberOfMonthsEt.setText("")
        }
        if (monthlyPaymentEt.text != null){
            monthlyPaymentEt.setText("")
        }
        if (totalCostEt.text != null){
            totalCostEt.setText("")
        }
        if (totalInterestsEt.text != null){
            totalInterestsEt.setText("")
        }
    }

    //------------------- Click on clear button -------------------------------------------------------

    private fun clickOnClear(){
        clearButton.setOnClickListener {
            clearChamps()
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Hide keyboard ------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun closeKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}