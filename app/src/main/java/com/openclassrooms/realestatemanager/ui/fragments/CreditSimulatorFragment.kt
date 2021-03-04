package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.droidman.ktoasty.showErrorToast
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.activities.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils
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
    private var monthPayment: Double = 0.0
    private var totalCost: Double = 0.0
    private var interests: Double = 0.0

    private lateinit var mainActivity: MainActivity
    private var currencyBoolean: Boolean = false
    private lateinit var eurosIconPrice: TextView
    private lateinit var dollarsIconPrice: TextView
    private lateinit var eurosIconContribution: TextView
    private lateinit var dollarsIconContribution: TextView
    private lateinit var eurosIconBorrowedAmount: TextView
    private lateinit var dollarsIconBorrowedAmount: TextView

    private lateinit var eurosIconMonthlyPayment: TextView
    private lateinit var dollarsIconMonthlyPayment: TextView
    private lateinit var eurosIconTotalCost: TextView
    private lateinit var dollarsIconTotalCost: TextView
    private lateinit var eurosIconTotalInterests: TextView
    private lateinit var dollarsIconTotalInterests: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_credit_simulator, container, false)
        mainActivity = MainActivity()
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
        eurosIconPrice = root.findViewById(R.id.simulator_price_euros)
        dollarsIconPrice = root.findViewById(R.id.simulator_price_dollars)
        eurosIconContribution = root.findViewById(R.id.simulator_contribution_euros)
        dollarsIconContribution = root.findViewById(R.id.simulator_contribution_dollars)
        eurosIconBorrowedAmount = root.findViewById(R.id.simulator_borrowed_amount_euros)
        dollarsIconBorrowedAmount = root.findViewById(R.id.simulator_borrowed_amount_dollars)
        eurosIconMonthlyPayment = root.findViewById(R.id.simulator_monthly_payment_euros)
        dollarsIconMonthlyPayment = root.findViewById(R.id.simulator_monthly_payment_dollars)
        eurosIconTotalCost = root.findViewById(R.id.simulator_total_cost_euros)
        dollarsIconTotalCost = root.findViewById(R.id.simulator_total_cost_dollars)
        eurosIconTotalInterests = root.findViewById(R.id.simulator_total_interests_euros)
        dollarsIconTotalInterests = root.findViewById(R.id.simulator_total_interests_dollars)
        //switchCurrencyIcon()
        return root
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Calculate cost with or without contribution -----------------
    //----------------------------------------------------------------------------------------------

    private fun calculatePriceWithContribution(){
        val priceSimulation: Double
        val contributionSimulation: Double
        val priceWithContributionSimulation: Double

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
        val rate: Double
        val totalCostSimulation: Double

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
        val totalInterests: Double = totalCost - price
        interests = totalInterests
        val df = DecimalFormat("#.##")
        val roundTotalInterests: String = df.format(totalInterests)
        totalInterestsEt.setText(roundTotalInterests)

    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Calculate monthly payment -----------------------------------
    //----------------------------------------------------------------------------------------------

    private fun calculateMonthlyPayment(){
        val numberOfMonths: Int
        val monthlyPayment: Double

        if (numberOfMonthsEt.text.isNullOrEmpty()){
            numberOfMonthsEt.error = getString(R.string.simulator_error_months)
        }
        else{
            numberOfMonths = numberOfMonthsEt.text.toString().toInt()
            monthlyPayment = totalCost / numberOfMonths
            monthPayment = monthlyPayment
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

    //------------------- Click on clear button ----------------------------------------------------

    private fun clickOnClear(){
        clearButton.setOnClickListener {
            clearChamps()
            eurosIconPrice.visibility = View.VISIBLE
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Hide keyboard -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun closeKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        switchCurrencyIcon()
    }

    private fun switchCurrencyIcon(){
        val bundle = arguments
        if (bundle != null) {
            val booleanOnClick = bundle.getBoolean("currencyBoolean")
            currencyBoolean = booleanOnClick

            when(currencyBoolean){
                true -> {
                    activity?.showSuccessToast("Boolean case 1 is $currencyBoolean", Toast.LENGTH_SHORT, true)
                    showEuroIcon()
                    showEurosPrice()
                }
                false -> {
                    activity?.showErrorToast("Boolean case 2 is $currencyBoolean", Toast.LENGTH_SHORT, true)
                    showDollarIcon()
                    showDollarsPrice()
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show € icon -------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun showEuroIcon(){
        eurosIconPrice.visibility = View.VISIBLE
        eurosIconPrice.visibility = View.VISIBLE
        eurosIconContribution.visibility = View.VISIBLE
        eurosIconBorrowedAmount.visibility = View.VISIBLE
        eurosIconMonthlyPayment.visibility = View.VISIBLE
        eurosIconTotalCost.visibility = View.VISIBLE
        eurosIconTotalInterests.visibility = View.VISIBLE
        dollarsIconPrice.visibility = View.VISIBLE

        dollarsIconPrice.visibility = View.GONE
        dollarsIconContribution.visibility = View.GONE
        dollarsIconBorrowedAmount.visibility = View.GONE
        dollarsIconMonthlyPayment.visibility = View.GONE
        dollarsIconTotalCost.visibility = View.GONE
        dollarsIconTotalInterests.visibility = View.GONE
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show $ icon -------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun showDollarIcon(){
        eurosIconPrice.visibility = View.GONE
        eurosIconPrice.visibility = View.GONE
        eurosIconContribution.visibility = View.GONE
        eurosIconBorrowedAmount.visibility = View.GONE
        eurosIconMonthlyPayment.visibility = View.GONE
        eurosIconTotalCost.visibility = View.GONE
        eurosIconTotalInterests.visibility = View.GONE
        dollarsIconPrice.visibility = View.GONE

        dollarsIconPrice.visibility = View.VISIBLE
        dollarsIconContribution.visibility = View.VISIBLE
        dollarsIconBorrowedAmount.visibility = View.VISIBLE
        dollarsIconMonthlyPayment.visibility = View.VISIBLE
        dollarsIconTotalCost.visibility = View.VISIBLE
        dollarsIconTotalInterests.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show € price ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun showEurosPrice(){
        val eurosHousePrice = Utils.convertDollarToEuro(price.toInt())
        val eurosContribution = Utils.convertDollarToEuro(contribution!!.toInt())
        val eurosBorrowedAmount = Utils.convertDollarToEuro(priceWithContribution.toInt())
        val eurosMonthlyPayment = Utils.convertDollarToEuro(monthPayment.toInt())
        val eurosTotalCost = Utils.convertDollarToEuro(totalCost.toInt())
        val eurosTotalInterests = Utils.convertDollarToEuro(interests.toInt())

        priceEt.setText(eurosHousePrice.toDouble().toString())
        contributionEt.setText(eurosContribution.toDouble().toString())
        borrowedAmountEt.setText(eurosBorrowedAmount.toDouble().toString())
        monthlyPaymentEt.setText(eurosMonthlyPayment.toDouble().toString())
        totalCostEt.setText(eurosTotalCost.toDouble().toString())
        totalInterestsEt.setText(eurosTotalInterests.toDouble().toString())
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show $ price ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun showDollarsPrice(){
        priceEt.setText(price.toString())
        contributionEt.setText(contribution!!.toString())
        borrowedAmountEt.setText(borrowedAmountEt.toString())
        monthlyPaymentEt.setText(monthPayment.toString())
        totalCostEt.setText(totalCost.toString())
        totalInterestsEt.setText(interests.toString())
    }
}