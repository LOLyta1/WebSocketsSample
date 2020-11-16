package com.tsivileva.nata.common

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tsivileva.nata.common.GetOrderBookUseCase
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.webSocket.entity.ConnectionStatus
import com.tsivileva.nata.core.webSocket.entity.Order

class OrderViewModel @ViewModelInject constructor(
    private val getOrderBookUseCase: GetOrderBookUseCase
) : ViewModel() {

    private var orders: LiveData<Order> = MutableLiveData()
    private var connectionStatus: LiveData<ConnectionStatus> = MutableLiveData()

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
            getOrderBookUseCase.setCurrency(fromCurrency, toCurrency)
            getOrderBookUseCase.connectToServer()
    }

    fun getOrders(lifecycleOwner: LifecycleOwner): LiveData<Order> {
        orders.removeObservers(lifecycleOwner)
        orders = getOrderBookUseCase.getData(viewModelScope)
        return orders
    }

    fun disconnect() {
        getOrderBookUseCase.disconnectFromServer()

    }

    fun subscribeOnConnectionStatus(lifecycleOwner: LifecycleOwner): LiveData<ConnectionStatus> {
        connectionStatus.removeObservers(lifecycleOwner)
        connectionStatus = getOrderBookUseCase.subscribeConnectionStatus(viewModelScope)
        return connectionStatus
    }

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }
}