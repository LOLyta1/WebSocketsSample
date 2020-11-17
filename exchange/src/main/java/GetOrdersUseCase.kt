package com.tsivileva.nata.exchange

import android.content.Context
import com.tsivileva.nata.core.WEB_SOCKET_ENDPOINT_ORDERS_PATH
import com.tsivileva.nata.core.getExchange
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.Exchange
import com.tsivileva.nata.core.model.ExchangeType
import com.tsivileva.nata.core.model.dto.WebSocketCommand
import com.tsivileva.nata.core.WebSocketUtils
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.network.OrderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
    @ApplicationContext private val context: Context
) {
    private var params = ""
    private var subscriberId: Int = 0

    @InternalCoroutinesApi
    suspend fun subscribeOnStream(
        currencies: Pair<Currency, Currency>,
        subscriberId: Int
    ): Flow<Order> {
        this.subscriberId = subscriberId
        params = WebSocketUtils.createOrderRequestString(
            currencies,
            context,
            WEB_SOCKET_ENDPOINT_ORDERS_PATH
        )

        val request = WebSocketUtils.createRequest(
            subscriberId,
            WebSocketCommand.Subscribe,
            listOf(params)
        )
        repository.sendRequest(request)
        return repository.getData(currencies)
    }

    fun subscribeOnSocketEvent() =
        repository.subscribeOnSocketEvent()

    fun unsubscribe() {
        val request = WebSocketUtils.createRequest(
            subscriberId,
            WebSocketCommand.Unsubscribe,
            listOf(params)
        )
        repository.sendRequest(request)
    }

    /*private var from: Currency? = null
    private var to: Currency? = null

    fun setCurrency(from: Currency, to: Currency) {
        this.from = from
        this.to = to
    }

    private fun isCurrencySet() = from != null && to != null

    var isConnected = webSocketClient.isConnected

    fun connectToServer() {
        if (isCurrencySet()) {
            webSocketClient.setParams(from!!, to!!, ORDERS_PATH, context)
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
                val snapshot =  restClient.loadSnapshot(from!!.getName(context), to!!.getName(context))

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
        webSocketClient.subscribeOnConnectionStatus(scope)*/

}
