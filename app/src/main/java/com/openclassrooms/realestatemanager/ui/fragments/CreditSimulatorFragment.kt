package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.MainActivityViewModel
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
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var isCurrencyChanged: Boolean = false
    //------------------- Price conversion ---------------------------------------------------------
    private var eurosHousePrice = 0.0
    private var eurosContribution = 0.0
    private var eurosBorrowedAmount = 0.0
    private var eurosMonthlyPayment = 0.0
    private var eurosTotalCost = 0.0
    private var eurosTotalInterests = 0.0
    private var df = DecimalFormat("#.##")

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
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        configureMainActivityViewModel()
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
            val roundPrice: String = df.format(price)
            priceEt.setText(roundPrice)

            contribution = contributionSimulation
            val roundContribution: String = df.format(contribution)
            contributionEt.setText(roundContribution)

            priceWithContribution = priceWithContributionSimulation
            val roundPriceWithContribution: String = df.format(priceWithContribution)
            borrowedAmountEt.setText(roundPriceWithContribution)
        }
        else if (!priceEt.text.isNullOrEmpty() && contributionEt.text.isNullOrEmpty()){
            val roundPrice: String = df.format(price)
            borrowedAmountEt.setText(roundPrice)
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
            val roundRate: String = df.format(rate)
            rateEt.setText(roundRate)

            totalCostSimulation = price + (price * (rate / 100))
            totalCost = totalCostSimulation
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
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Hide keyboard -----------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun closeKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Configure MainActivityViewModel -----------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureMainActivityViewModel(){
        mainActivityViewModel.isClickedCurrency().observe(requireActivity(), Observer {
            isCurrencyChanged = it
            switchCurrencyIcon()
        })
    }

    private fun switchCurrencyIcon(){

        when(isCurrencyChanged){
            true -> {
                showEuroIcon()
                showEurosPrice()
            }
            false -> {
                showDollarIcon()
                showDollarsPrice()
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
        val theEurosHousePrice = Utils.convertDollarToEuro(price.toInt()).toDouble()
        eurosHousePrice = theEurosHousePrice
        val theEurosContribution = Utils.convertDollarToEuro(contribution!!.toInt()).toDouble()
        eurosContribution = theEurosContribution
        val theEurosBorrowedAmount = Utils.convertDollarToEuro(priceWithContribution.toInt()).toDouble()
        eurosBorrowedAmount = theEurosBorrowedAmount
        val theEurosMonthlyPayment = Utils.convertDollarToEuro(monthPayment.toInt()).toDouble()
        eurosMonthlyPayment = theEurosMonthlyPayment
        val theEurosTotalCost = Utils.convertDollarToEuro(totalCost.toInt()).toDouble()
        eurosTotalCost = theEurosTotalCost
        val theEurosTotalInterests = Utils.convertDollarToEuro(interests.toInt()).toDouble()
        eurosTotalInterests = theEurosTotalInterests

        priceEt.setText(eurosHousePrice.toString())
        contributionEt.setText(eurosContribution.toString())
        borrowedAmountEt.setText(eurosBorrowedAmount.toString())
        monthlyPaymentEt.setText(eurosMonthlyPayment.toString())
        totalCostEt.setText(eurosTotalCost.toString())
        totalInterestsEt.setText(eurosTotalInterests.toString())
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Show $ price ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun showDollarsPrice(){
        val dollarsHousePrice = Utils.convertEuroToDollar(eurosHousePrice.toInt()).toDouble()
        val dollarsContribution = Utils.convertEuroToDollar(eurosContribution.toInt()).toDouble()
        val dollarsBorrowedAmount = Utils.convertEuroToDollar(eurosBorrowedAmount.toInt()).toDouble()
        val dollarsMonthlyPayment = Utils.convertEuroToDollar(eurosMonthlyPayment.toInt()).toDouble()
        val dollarsTotalCost = Utils.convertEuroToDollar(eurosTotalCost.toInt()).toDouble()
        val dollarsTotalInterests = Utils.convertEuroToDollar(eurosTotalInterests.toInt()).toDouble()

        priceEt.setText(dollarsHousePrice.toString())
        contributionEt.setText(dollarsContribution.toString())
        borrowedAmountEt.setText(dollarsBorrowedAmount.toString())
        monthlyPaymentEt.setText(dollarsMonthlyPayment.toString())
        totalCostEt.setText(dollarsTotalCost.toString())
        totalInterestsEt.setText(dollarsTotalInterests.toString())
    }
}