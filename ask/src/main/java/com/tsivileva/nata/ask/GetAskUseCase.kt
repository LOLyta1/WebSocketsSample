package com.tsivileva.nata.ask

import android.content.Context
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.webSocket.ORDERS_PATH
import com.tsivileva.nata.network.socket.WebSocketClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetAskUseCase @Inject constructor(
    private val client: WebSocketClient,
    @ApplicationContext private val context: Context
) {
    private lateinit var from: Currency
    private lateinit var to: Currency

    fun setCurrency(from: Currency, to: Currency) {
        this.from = from
        this.to = to
    }

    fun connectToServer() {
        client.setParams(from, to, ORDERS_PATH, context)
        client.connect()
    }

    fun disconectFromServer() {
        client.close()
    }

    fun getData() =
        client.getStream()

    fun subscribeConnectionStatus(scope: CoroutineScope) =
        client.subscribeOnConnectionStatus(scope)

/*
    private var fromCurrency: Currency? = null
    private var toCurrency: Currency? = null

    fun setExchange(fromCurrency: Currency, toCurrency: Currency) {
        this.fromCurrency = fromCurrency
        this.toCurrency = toCurrency
    }

    fun getOrders(): Flow<Order> {
        val request = createConnectionRequest(WebSocketCommand.Subscribe)
        networkRepositoryImpl.service.sendRequest(request)
        return networkRepositoryImpl.getData()
    }

    private fun createConnectionRequest(
        command: WebSocketCommand
    ): SocketRequest {
        val endpoint = Endpoints.OrdersListSocket
        val param = fromCurrency?.getName(context) + toCurrency?.getName(context) + endpoint.value
        return SocketRequest(
            connectionCommand = command.name,
            params = arrayListOf(param.toLowerCase(Locale.ROOT))
        )
    }

    fun closeConnection() {
        val request = createConnectionRequest(WebSocketCommand.Unsubscribe)
        networkRepositoryImpl.service.sendRequest(request)
    }

    fun subscribeOnRequestStatus(scope: CoroutineScope): LiveData<ConnectionStatus> {
        val statusLiveData = MutableLiveData<ConnectionStatus>()
        scope.launch {
            networkRepositoryImpl.service.observeWebSocketEvent().collect {
                Timber.d("event is - ${it}")
                if (it is WebSocket.Event.OnConnectionOpened<*>) {
                    statusLiveData.postValue(ConnectionStatus.Opened)
                }

                if (it is WebSocket.Event.OnConnectionFailed) {
                    statusLiveData.postValue(
                        ConnectionStatus.Failed(
                            error = it.throwable.message ?: ""
                        )
                    )
                }

                if (it is WebSocket.Event.OnConnectionClosed) {
                    statusLiveData.postValue(ConnectionStatus.Closed)
                }

            }
        }
        return statusLiveData
    }*/


}
