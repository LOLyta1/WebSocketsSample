package com.tsivileva.nata.network.socket

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.NetworkClient
import com.tsivileva.nata.core.model.Order
import com.tsivileva.nata.core.model.webSocket.WebSocketCommand
import com.tsivileva.nata.core.model.webSocket.ConnectionStatus
import com.tsivileva.nata.core.model.webSocket.SocketRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class WebSocketClient(
    private var service: BinanceService
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
        service.sendRequest(request)
    }


    override fun subscribeOnConnectionStatus(scope: CoroutineScope): LiveData<ConnectionStatus> {
        val statusLiveData = MutableLiveData<ConnectionStatus>()
        scope.launch {
            service.observeWebSocketEvent().collect {
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
        service.sendRequest(request)
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
        service.observeOrdersTicker()

}