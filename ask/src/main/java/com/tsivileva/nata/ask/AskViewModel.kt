package com.tsivileva.nata.ask

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.tsivileva.nata.core.model.Order

class AskViewModel @ViewModelInject constructor(
    private val getAskUseCase: GetAskUseCase
) : ViewModel() {

    val orders = MutableLiveData<Order>()

    fun subscribeOnOrderBook() {
        viewModelScope.launch {
           getAskUseCase().collect {
                orders.postValue(it)
           }
        }
    }
}