package com.tsivileva.nata.network

import android.content.Context
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.core.model.dto.SocketRequest
import com.tsivileva.nata.network.rest.OrderRestClient
import com.tsivileva.nata.network.socket.OrderWebSocketClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val webSocketClient: OrderWebSocketClient,
    private val restClient: OrderRestClient,
    @ApplicationContext private var context: Context
) {
    fun subscribeOnSocketEvent() = webSocketClient.subscribeOnSocketEvent()
    fun sendRequest(request: SocketRequest) = webSocketClient.sendRequest(request)

    @InternalCoroutinesApi
    suspend fun getData(currencies: Pair<Currency, Currency>): Flow<Order> {
        var order = Order()
        val snapshot = restClient.loadSnapshot(
            currencies.first.getName(context),
            currencies.second.getName(context)
        )
       var flow = webSocketClient.getData().map {
            it.ask = deleteEmpty(it.ask)
            it.bids = deleteEmpty(it.bids)
            it
        }.filter {
            it.lastUpdateId > snapshot.lastUpdateId + 1
        }/*.collect {
            Timber.d("lastU = ${it.lastUpdateId}, firstU = ${it.firstUpdateId}")
            order = it
        }*/
        return flow
    }

    private fun deleteEmpty(list: List<List<String>>): List<List<String>> {
        return list.toSet().filter {
            val beforePoint = it.last().split(".")[0]
            beforePoint.toInt() > 0
        }.toList()
    }

    /* private var from: Currency? = null
     private var to: Currency? = null

     fun setCurrency(from: Currency, to: Currency) {
         this.from = from
         this.to = to
     }

     private fun isCurrencySet() = from != null && to != null

     var isConnected = webSocketClient.isConnected

     fun connectToServer() {
         if (isCurrencySet()) {
           //  webSocketClient.setParams(from!!, to!!, ORDERS_PATH, context)
             webSocketClient.getData()
         }
     }

     fun unsubscribe() {
         webSocketClient.close()
     }

     @FlowPreview
     @ExperimentalCoroutinesApi
     fun getData(scope: CoroutineScope): LiveData<Order> {
         val liveData = MutableLiveData<Order>()
         scope.launch {

             if (isCurrencySet()) {
                 val snapshot =
                     restClient.loadSnapshot(from!!.getName(context), to!!.getName(context))

                 webSocketClient.getStream().map {
                     it.ask = deleteEmpty(it.ask)
                     it.bids = deleteEmpty(it.bids)
                     it
                 }.filter {
                     it.lastUpdateId > snapshot.lastUpdateId + 1
                 }.collect {
                     Timber.d("lastU = ${it.lastUpdateId}, firstU = ${it.firstUpdateId}")
                     liveData.postValue(it)
                 }
             }
         }
         return liveData
     }

     private fun deleteEmpty(list: List<List<String>>): List<List<String>> {
         return list.toSet().filter {
             val beforePoint = it.last().split(".")[0]
             beforePoint.toInt() > 0
         }.toList()
     }

     fun subscribeConnectionStatus(scope: CoroutineScope) =
         webSocketClient.getData()*/

}
