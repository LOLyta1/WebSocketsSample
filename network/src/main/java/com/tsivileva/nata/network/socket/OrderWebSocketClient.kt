package com.tsivileva.nata.network.socket

import com.tinder.scarlet.WebSocket
import com.tsivileva.nata.core.NetworkClient
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.core.model.dto.SocketRequest
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class OrderWebSocketClient(
    private val api: SocketApi
) : NetworkClient.WebSocket<Order> {

    override fun subscribeOnSocketEvent(): Flow<WebSocket.Event> = api.subscribeOnConnection()

    override fun sendRequest(request: SocketRequest) {
        Timber.d("OnConnection REQUEST: $request")
        api.sendRequest(request)
    }

    override fun getData(): Flow<Order> = api.getData()

}
/*

private var params = mutableListOf<String>()

fun setParams(
    fromCurrency: Currency,
    toCurrency: Currency,
    apiPath: String,
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

override fun subscribeOnConnectionStatus(scope: CoroutineScope): LiveData<ConnectionStatus> {
    val statusLiveData = MutableLiveData<ConnectionStatus>()
    scope.launch {
        api.observeOnSocketEvent().collect {
            if (it is WebSocket.Event.OnConnectionOpened<*>) {
                Timber.d("STATE OnConnectionOpened")
                statusLiveData.postValue(ConnectionStatus.Opened)
                isConnected = true
            }


            if (it is WebSocket.Event.OnConnectionFailed) {
                Timber.d("STATE OnConnectionFailed")
                statusLiveData.postValue(
                    ConnectionStatus.Failed(
                        error = it.throwable.message ?: ""
                    )
                )
                isConnected = false
            }

            if (it is WebSocket.Event.OnConnectionClosed) {
                Timber.d("STATE OnConnectionClosed")
                statusLiveData.postValue(ConnectionStatus.Closed)
                isConnected = false
            }

            if (it is WebSocket.Event.OnMessageReceived) {
                Timber.d("STATE OnMessageReceived")
            }
        }
    }
    return statusLiveData
}

override fun getStream(): Flow<Order> =
    api.observeOnOrderStream()

override fun close() {
    val request = createRequest(WebSocketCommand.Unsubscribe, params)
    api.sendRequest(request)
}
*/
