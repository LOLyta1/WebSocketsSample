package com.tsivileva.nata.network.socket

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.NetworkClient
import com.tsivileva.nata.core.webSocket.entity.Order
import com.tsivileva.nata.core.webSocket.entity.WebSocketCommand
import com.tsivileva.nata.core.webSocket.entity.ConnectionStatus
import com.tsivileva.nata.core.webSocket.entity.SocketRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class OrderWebSocketClient(
    private val api: SocketApi
) : NetworkClient.WebSocket<Order> {

    private var params = mutableListOf<String>()

    fun setParams(
        fromCurrency: Currency,
        toCurrency: Currency,
        apiPath:String,
        context: Context
    ) {
        val param = fromCurrency.getName(context) +
                toCurrency.getName(context) +
                apiPath
        params.clear()
        params.add(param)
    }

    override var isConnected = false

    override fun connect() {
        val request = createRequest(WebSocketCommand.Subscribe, params)
        api.sendRequest(request)
    }


    override fun subscribeOnConnectionStatus(scope: CoroutineScope): LiveData<ConnectionStatus> {
        val statusLiveData = MutableLiveData<ConnectionStatus>()
        scope.launch {
            api.observeWebSocketEvent().collect {
                if (it is WebSocket.Event.OnConnectionOpened<*>) {
                    statusLiveData.postValue(ConnectionStatus.Opened)
                }


                if (it is WebSocket.Event.OnConnectionFailed) {
                    statusLiveData.postValue(
                        ConnectionStatus.Failed(
                            error = it.throwable.message ?: ""
                        )
                    )
                    isConnected = false
                }

                if (it is WebSocket.Event.OnConnectionClosed) {
                    statusLiveData.postValue(ConnectionStatus.Closed)
                    isConnected = false
                }
            }
        }
        return statusLiveData
    }

    override fun close() {
        val request = createRequest(WebSocketCommand.Unsubscribe, params)
        api.sendRequest(request)
    }


    override fun createRequest(command: WebSocketCommand, params: List<String>): SocketRequest {
        val lowerCaseParams = params.map {
            it.toLowerCase(Locale.ROOT)
        }
       val request = SocketRequest(
            method = command.name,
            params = lowerCaseParams
        )
        Timber.d("REQUEST = $request")
        return request
    }

    override fun getStream(): Flow<Order> =
        api.observeOrdersTicker()

}