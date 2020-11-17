package com.tsivileva.nata.exchange.bid

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.Transformations
import com.tinder.scarlet.WebSocket
import com.tsivileva.nata.core.model.Exchange
import com.tsivileva.nata.core.model.ExchangeType
import com.tsivileva.nata.core.getExchange
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.ConnectionStatus
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.exchange.GetOrdersUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class BidViewModel@ViewModelInject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    var isConnected = false
    private var orders = MutableLiveData<Exchange>()
    private var connectionStatus = MutableLiveData<ConnectionStatus>()

    /*fun isConnected() =
        getOrdersUseCase.isConnected

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
            getOrdersUseCase.setCurrency(fromCurrency, toCurrency)
            getOrdersUseCase.connectToServer()
    }*/
    @InternalCoroutinesApi
    fun getOrders(currencies: Pair<Currency, Currency>): LiveData<Exchange> {
        viewModelScope.launch {
                getOrdersUseCase.subscribeOnStream(currencies).collect {
                    orders.postValue(it.getExchange(ExchangeType.Bid))
                }

        }
        return orders
    }

    fun subscribeOnEvents(): LiveData<ConnectionStatus> {
        viewModelScope.launch {
            getOrdersUseCase.subscribeOnSocketEvent().collect {
                if (it is WebSocket.Event.OnConnectionOpened<*>) {
                    Timber.d("STATE OnConnectionOpened")
                    connectionStatus.postValue(ConnectionStatus.Opened)
                    isConnected = true
                }


                if (it is WebSocket.Event.OnConnectionFailed) {
                    Timber.d("STATE OnConnectionFailed")
                    connectionStatus.postValue(
                        ConnectionStatus.Failed(it.throwable.message ?: "")
                    )
                    isConnected = false
                }

                if (it is WebSocket.Event.OnConnectionClosed) {
                    Timber.d("STATE OnConnectionClosed")
                    connectionStatus.postValue(ConnectionStatus.Closed)
                    isConnected = false
                }

                if (it is WebSocket.Event.OnMessageReceived) {
                    Timber.d("STATE OnMessageReceived")
                }
            }
        }
        return connectionStatus
    }

    fun unsubscribe() {
        getOrdersUseCase.unsubscribe()
    }
}/*
    private var scope: CoroutineScope? = null
    private var orders: LiveData<Order> = MutableLiveData()
    private var connectionStatus: LiveData<ConnectionStatus> = MutableLiveData()

    fun isConnected() =
        getOrdersUseCase.isConnected

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
            getOrdersUseCase.setCurrency(fromCurrency, toCurrency)
            getOrdersUseCase.connectToServer()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getOrders(lifecycleOwner: LifecycleOwner): LiveData<Exchange> {
        Timber.d("GET OBSERVERS START")
        orders.removeObservers(lifecycleOwner)
        scope = CoroutineScope(Dispatchers.IO)
        orders = getOrdersUseCase.getData(scope!!)
        return Transformations.map(orders) {
            it.getExchange(ExchangeType.Bid)
        }
    }

    fun unsubscribe() {
        getOrdersUseCase.unsubscribe()
    }

    fun subscribeOnConnectionStatus(lifecycleOwner: LifecycleOwner): LiveData<ConnectionStatus> {
        connectionStatus.removeObservers(lifecycleOwner)
        scope = CoroutineScope(Dispatchers.IO)
        connectionStatus = getOrdersUseCase.subscribeConnectionStatus(scope!!)
        return connectionStatus
    }

    fun release() {
        unsubscribe()
        scope?.cancel()
    }

    override fun onCleared() {
        release()
        super.onCleared()
    }*/
