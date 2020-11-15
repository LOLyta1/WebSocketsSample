package com.tsivileva.nata.ask

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.Order
import com.tsivileva.nata.core.model.webSocket.ConnectionStatus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AskViewModel @ViewModelInject constructor(
    private val getAskUseCase: GetAskUseCase
) : ViewModel() {

    var orders = MutableLiveData<Order>()

    private var connectionStatus: LiveData<ConnectionStatus> = MutableLiveData()

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
        getAskUseCase.setCurrency(fromCurrency, toCurrency)
        getAskUseCase.connectToServer()
    }

    fun getOrders() {
        viewModelScope.launch {
            getAskUseCase.getData().collect {
                orders.postValue(it)
            }
        }
    }

    fun disconnect() {
        getAskUseCase.disconectFromServer()
    }

    fun subscribeOnConnectionStatus(lifecycleOwner: LifecycleOwner): LiveData<ConnectionStatus> {
        connectionStatus.removeObservers(lifecycleOwner)
        connectionStatus = getAskUseCase.subscribeConnectionStatus(viewModelScope)
        return connectionStatus
    }

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }
}