package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private var isClickedCurrency = MutableLiveData<Boolean>()
    private var isClickedRefresh = MutableLiveData<Boolean>()

    //-------------------------------- Change currency ---------------------------------------------

    fun isClickedCurrency(): LiveData<Boolean>{
        return isClickedCurrency
    }

    fun clickDollarsToEuros(){
        isClickedCurrency.value = true
    }

    fun clickEurosToDollars(){
        isClickedCurrency.value = false
    }

    //-------------------------------- Refresh house list ------------------------------------------

    fun isClickedRefresh(): LiveData<Boolean>{
        return isClickedRefresh
    }

    fun clickToRefresh(){
        isClickedRefresh.value = true
    }
}