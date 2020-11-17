package com.tsivileva.nata.network

import android.content.Context
import androidx.lifecycle.*
import com.tinder.scarlet.WebSocket
import com.tsivileva.nata.core.WEB_SOCKET_ENDPOINT_ORDERS_PATH
import com.tsivileva.nata.core.WebSocketUtils
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.NetworkResponse
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.core.model.dto.WebSocketCommand
import com.tsivileva.nata.network.rest.OrderRestClient
import com.tsivileva.nata.network.socket.OrderWebSocketClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val webSocketClient: OrderWebSocketClient,
    private val restClient: OrderRestClient,
    @ApplicationContext private var context: Context
) {

    private var currencies: Pair<Currency, Currency> = Pair(Currency.Bitcoin, Currency.Bitcoin)
    private var pair = Pair(Currency.Bitcoin, Currency.Tether)

    private fun createRequestStringForOrders(
        currencies: Pair<Currency, Currency>,
        apiPath: String
    ): String {
        return currencies.first.getName(context) +
                currencies.second.getName(context) +
                apiPath
    }

    @InternalCoroutinesApi
    suspend fun sendCommandToSubscribe(
        currencies: Pair<Currency, Currency>
    ) {
        this.currencies = currencies
        val requestParam = createRequestStringForOrders(currencies, WEB_SOCKET_ENDPOINT_ORDERS_PATH)
        webSocketClient.sendRequest(
            WebSocketUtils.createRequest(WebSocketCommand.Subscribe, listOf(requestParam))
        )
    }


    @InternalCoroutinesApi
    suspend fun getData(currencies: Pair<Currency, Currency>): Flow<Order> {
        val snapshot = restClient.loadSnapshot(
            currencies.first.getName(context),
            currencies.second.getName(context)
        )
        return webSocketClient.getData().map {
            it.ask = deleteEmpty(it.ask)
            it.bids = deleteEmpty(it.bids)
            it
        }.filter {
            it.lastUpdateId > snapshot.lastUpdateId + 1
        }
    }

    fun unsubscribe() {
        val requestParam = createRequestStringForOrders(currencies, WEB_SOCKET_ENDPOINT_ORDERS_PATH)
        webSocketClient.sendRequest(
            WebSocketUtils.createRequest(WebSocketCommand.Unsubscribe, listOf(requestParam))
        )
    }

    //TODO: this test method
    @InternalCoroutinesApi
    suspend fun getLiveData(currencies: Pair<Currency, Currency>): LiveData<NetworkResponse<Order>> {
        val liveData = MutableLiveData<NetworkResponse<Order>>()
        try {
            val snapshot = restClient.loadSnapshot(
                currencies.first.getName(context),
                currencies.second.getName(context)
            )
            webSocketClient.getData().map {
                it.ask = deleteEmpty(it.ask)
                it.bids = deleteEmpty(it.bids)
                it
            }.filter {
                it.lastUpdateId > snapshot.lastUpdateId + 1
            }.collect {
                liveData.postValue(NetworkResponse.Successful(it))
            }
        } catch (exception: Exception) {
            liveData.postValue(NetworkResponse.Error(exception.message.toString()))
        }
        return liveData
    }


    private fun deleteEmpty(list: List<List<String>>): List<List<String>> {
        return list.toSet().filter {
            val beforePoint = it.last().split(".")[0]
            beforePoint.toInt() > 0
        }.toList()
    }

    //TODO:Replace in base interface|class
    @FlowPreview
    @InternalCoroutinesApi
    suspend fun subscribeOnSocketEvent(
        onSubscribed: suspend () -> Unit
    ) {
       webSocketClient.subscribeOnSocketEvent().collect {
            when (it) {
                is WebSocket.Event.OnConnectionOpened<*> -> {
                    Timber.d("OnConnectionOpened ")
                }
                is WebSocket.Event.OnMessageReceived -> {
                    Timber.d(
                        "OnMessageReceived(cropped) , state = ${
                            it.message.toString().substring(1..30)
                        }"
                    )
                    onSubscribed()
                }
                is WebSocket.Event.OnConnectionClosing -> {
                    Timber.d("OnConnectionClosing ")
                }
                is WebSocket.Event.OnConnectionClosed -> {
                    Timber.d("OnConnectionClosed ")

                }
                is WebSocket.Event.OnConnectionFailed -> {
                    Timber.d("OnConnectionFailed ")
                }
            }

        }
    }


}
