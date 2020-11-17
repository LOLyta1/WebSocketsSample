package com.tsivileva.nata.exchange.statistic

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tinder.scarlet.WebSocket
import com.tsivileva.nata.core.model.*
import com.tsivileva.nata.exchange.GetOrdersUseCase
import kotlinx.coroutines.*
import timber.log.Timber

class StatisticViewModel @ViewModelInject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    var isConnected = false
    private var statistic = MutableLiveData<Statistic>()
    private var connectionStatus = MutableLiveData<ConnectionStatus>()

    /*fun isConnected() =
        getOrdersUseCase.isConnected

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
            getOrdersUseCase.setCurrency(fromCurrency, toCurrency)
            getOrdersUseCase.connectToServer()
    }*//*
    @InternalCoroutinesApi
    fun getStatistic(currencies: Pair<Currency, Currency>): LiveData<Statistic> {
        viewModelScope.launch {
            getOrdersUseCase.sendRequestForSubscribe(currencies).collect {
                statistic.postValue(it.getStatistic())
            }
        }
        return statistic*/
    }/*

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
}*/

/*
    private var scope: CoroutineScope? = null
    private var orders: LiveData<Order> = MutableLiveData()
    private var connectionStatus: LiveData<ConnectionStatus> = MutableLiveData()

    fun isConnected() = getOrdersUseCase.isConnected

    fun setCurrenciesAndConnect(fromCurrency: Currency, toCurrency: Currency) {
        //TODO: deleted from here check on estibilished connectiom
            getOrdersUseCase.setCurrency(fromCurrency, toCurrency)
            getOrdersUseCase.connectToServer()
    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getOrders(lifecycleOwner: LifecycleOwner): LiveData<Statistic> {
        Timber.d("GET OBSERVERS START")
        orders.removeObservers(lifecycleOwner)
        scope = CoroutineScope(Dispatchers.IO)
        orders = getOrdersUseCase.getData(scope!!)
        return Transformations.map(orders) {
            it.getStatistic()
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
