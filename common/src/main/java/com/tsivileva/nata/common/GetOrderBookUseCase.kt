package com.tsivileva.nata.common

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.webSocket.entity.ORDERS_PATH
import com.tsivileva.nata.core.webSocket.entity.Order
import com.tsivileva.nata.network.rest.OrderRestClient
import com.tsivileva.nata.network.socket.OrderWebSocketClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val STREAM_TIMEOUT = 5000L

class GetOrderBookUseCase @Inject constructor(
    private val webSocketClient: OrderWebSocketClient,
    private val restClient: OrderRestClient,
    @ApplicationContext private val context: Context
) {
    private var from: Currency? = null
    private var to: Currency? = null

    fun setCurrency(from: Currency, to: Currency) {
        this.from = from
        this.to = to
    }

    private fun isCurrencySet() = from != null && to != null

    fun connectToServer() {
        if (isCurrencySet()) {
            webSocketClient.setParams(from!!, to!!, ORDERS_PATH, context)
            webSocketClient.connect()
        }
    }

    fun disconnectFromServer() {
        webSocketClient.close()
    }

    fun getData(scope: CoroutineScope): LiveData<Order> {
        val liveData = MutableLiveData<Order>()
        scope.launch {
            if (isCurrencySet()) {
                var snapshot =  restClient.loadSnapshot(from!!.getName(context), to!!.getName(context))

                webSocketClient.getStream()
                    .filter {
                        it.lastUpdateId > snapshot.lastUpdateId
                    }.map {
                        it.ask = deleteEmpty(it.ask)
                        it.bids = deleteEmpty(it.bids)
                        it
                    }.collect {
                        liveData.postValue(it)
                        delay(STREAM_TIMEOUT)
                    }
            }
        }
        return liveData
    }

    private fun deleteEmpty(list: List<List<String>>): List<List<String>> {
        val deletedList = list.toSet().filter {
            val beforePoint = it.last().split(".")[0]
            beforePoint.toInt() > 0
        }.toList()
        Timber.d("DELETED list = ${deletedList}")
        return deletedList
    }

    fun subscribeConnectionStatus(scope: CoroutineScope) =
        webSocketClient.subscribeOnConnectionStatus(scope)

}
