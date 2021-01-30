package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private var isClickedCurrency = MutableLiveData<Boolean>()
    fun isClickedCurrency(): LiveData<Boolean>{
        return isClickedCurrency
    }

    fun clickDollarsToEuros(){
        isClickedCurrency.value = true
    }

    fun clickEurosToDollars(){
        isClickedCurrency.value = false
    }
}