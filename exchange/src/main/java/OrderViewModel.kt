package com.tsivileva.nata.exchange
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.Transformations
import com.tsivileva.nata.core.model.Exchange
import com.tsivileva.nata.core.model.ExchangeType
import com.tsivileva.nata.core.getExchange
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.ConnectionStatus
import com.tsivileva.nata.core.model.dto.Order

class OrderViewModel @ViewModelInject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private var orders: LiveData<Order> = MutableLiveData()
    private var connectionStatus: LiveData<ConnectionStatus> = MutableLiveData()

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
        getOrdersUseCase.setCurrency(fromCurrency, toCurrency)
        getOrdersUseCase.connectToServer()
    }

    fun getOrders(lifecycleOwner: LifecycleOwner): LiveData<Exchange> {
        orders.removeObservers(lifecycleOwner)
        orders = getOrdersUseCase.getData(viewModelScope)
        return Transformations.map(orders) {
            it.getExchange(ExchangeType.Bid)
        }
    }

    fun disconnect() {
        getOrdersUseCase.unsubscribe()

    }

    fun subscribeOnConnectionStatus(lifecycleOwner: LifecycleOwner): LiveData<ConnectionStatus> {
        connectionStatus.removeObservers(lifecycleOwner)
        connectionStatus = getOrdersUseCase.subscribeConnectionStatus(viewModelScope)
        return connectionStatus
    }

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }
}