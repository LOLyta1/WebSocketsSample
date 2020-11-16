package com.tsivileva.nata.exchange.statistic

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tsivileva.nata.core.getExchange
import com.tsivileva.nata.core.getStatistic
import com.tsivileva.nata.core.model.*
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.exchange.GetOrdersUseCase
import kotlinx.coroutines.*
import timber.log.Timber

class StatisticViewModel @ViewModelInject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

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
    }
}