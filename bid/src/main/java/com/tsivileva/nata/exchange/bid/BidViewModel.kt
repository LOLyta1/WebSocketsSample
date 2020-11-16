package com.tsivileva.nata.exchange.bid

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.Transformations
import com.tsivileva.nata.common.GetOrderBookUseCase
import com.tsivileva.nata.core.entity.Exchange
import com.tsivileva.nata.core.entity.ExchangeType
import com.tsivileva.nata.core.getExchange
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.webSocket.entity.ConnectionStatus
import com.tsivileva.nata.core.model.Order
import kotlinx.coroutines.*
import timber.log.Timber

class BidViewModel@ViewModelInject constructor(
    private val getOrderBookUseCase: GetOrderBookUseCase
) : ViewModel() {

    private var scope: CoroutineScope? = null
    private var orders: LiveData<Order> = MutableLiveData()
    private var connectionStatus: LiveData<ConnectionStatus> = MutableLiveData()

    fun isConnected() =
        getOrderBookUseCase.isConnected

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
        if (!isConnected()) {
            getOrderBookUseCase.setCurrency(fromCurrency, toCurrency)
            getOrderBookUseCase.connectToServer()
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getOrders(lifecycleOwner: LifecycleOwner): LiveData<Exchange> {
        Timber.d("GET OBSERVERS START")
        orders.removeObservers(lifecycleOwner)
        scope = CoroutineScope(Dispatchers.IO)
        orders = getOrderBookUseCase.getData(scope!!)
        return Transformations.map(orders) {
            it.getExchange(ExchangeType.Bid)
        }
    }

    fun unsubscribe() {
        getOrderBookUseCase.unsubscribe()
    }

    fun subscribeOnConnectionStatus(lifecycleOwner: LifecycleOwner): LiveData<ConnectionStatus> {
        connectionStatus.removeObservers(lifecycleOwner)
        scope = CoroutineScope(Dispatchers.IO)
        connectionStatus = getOrderBookUseCase.subscribeConnectionStatus(scope!!)
        return connectionStatus
    }

    fun release() {
        unsubscribe()
        scope?.cancel()
    }

    override fun onCleared() {
        release()
        super.onCleared()
    }
}