package com.tsivileva.nata.exchange.bid

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tsivileva.nata.core.getExchange
import com.tsivileva.nata.core.model.*
import com.tsivileva.nata.exchange.GetOrdersUseCase
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.*
import com.tsivileva.nata.core.model.dto.Order
import kotlinx.coroutines.flow.*
import timber.log.Timber

class BidViewModel @ViewModelInject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    @ActivityContext private val context: Context
) : ViewModel() {

    private var ordersStream = MutableLiveData<NetworkResponse<Exchange>>()
    private var isConnected = false

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun getOrders(
        currencies: Pair<Currency, Currency>
    ): LiveData<NetworkResponse<Exchange>> {
        viewModelScope.launch {
            getOrdersUseCase.sendRequestForSubscribe(currencies)
            getOrdersUseCase.getOrder(currencies).collect {
            /*    .map { it.getExchange(ExchangeType.Bid) }*/
                Timber.d("Order callback worked WELL")
             ///   ordersStream.postValue(NetworkResponse.Successful(it))

            }
        }

        /* Timber.d("Order callback worked")
         val tempFlow = getOrdersUseCase.getOrder(currencies)
         saveFlow(tempFlow)
         flow?.map { it.getExchange(ExchangeType.Bid) }?.collect {
             Timber.d("Order callback worked WELL")

             ordersStream.postValue(NetworkResponse.Successful(it))
         }*/
        /*  getOrdersUseCase.getOrder(currencies).collect {
              Timber.d("Order callback worked - get order")
              val exchangeType = it.getExchange(ExchangeType.Bid)
              ordersStream.postValue(NetworkResponse.Successful(exchangeType))
          }*/
        /*   ?.map { it.getExchange(ExchangeType.Bid) }?.collect {
               Timber.d("Order FLOW")
               //Timber.d("Order callback worked - get order")
               //  val exchangeType = it.getExchange(ExchangeType.Bid)

               ordersStream.postValue(NetworkResponse.Successful(it))
           }*/



        Timber.d("getOrders")
        return ordersStream
    }


    private fun getOrder() {

    }
/*Transformations.map(liveData,{
        if(it is NetworkResponse.Successful){
            it.data
        }
    })*/
/*      try {
        getOrdersUseCase.sendRequestForSubscribe(currencies).onEach {
            Timber.d("IT - $it")
        }.collect {
            Timber.d("getOrders $it")

            val exchangeType = it.getExchange(ExchangeType.Bid)
            ordersStream.postValue(NetworkResponse.Successful(exchangeType))
        }
    } catch (e: Exception) {
        Timber.d("getOrders e=$e")

        ordersStream.postValue(NetworkResponse.Error(e.message ?: ""))
    }*/
//}
/* val status = getOrdersUseCase.sendRequestForSubscribe(currencies)
 Timber.d("ORDERS: status $status")
 when (status) {
     is ConnectionStatus.Opened -> {
         try {
             getOrdersUseCase.subscribeOnOrders(currencies).collect {
                 val exchange = it.getExchange(ExchangeType.Bid)
                 Timber.d("ORDERS: recieve order ${it.symbol}")
                 ordersStream.postValue(NetworkResponse.Successful(exchange))
             }
         } catch (error: Exception) {
             val msg = "${context.getText(R.string.httpFailed)} + ${error.message.toString()}"
             Timber.d("ORDERS: error $msg")
             ordersStream.postValue(NetworkResponse.Error(msg))
         }
     }

     is ConnectionStatus.Closed -> {
         val msg = context.getText(R.string.connectionClosed)
         Timber.d("ORDERS: error $msg")
         ordersStream.postValue(
             NetworkResponse.Error(msg.toString())
         )
     }

     is ConnectionStatus.Failed -> {
         val msg = context.getText(R.string.connectionFailed)
         Timber.d("ORDERS: error $msg")
         ordersStream.postValue(
             NetworkResponse.Error(msg.toString())
         )
     }
 }
}*/


/*
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

    fun unsubscribe() {
        getOrdersUseCase.unsubscribe()
    }
}