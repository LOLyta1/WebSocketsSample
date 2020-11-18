package com.tsivileva.nata.core

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.dto.Order
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import okhttp3.*
import okio.ByteString
import timber.log.Timber
import java.util.*
import javax.inject.Inject


sealed class SocketEvents<T> {
    class Sleep<T> : SocketEvents<T>()
    class Opened<T> : SocketEvents<T>()
    class Emitted<T>(var data: T) : SocketEvents<T>()
    class Closed<T> : SocketEvents<T>()
    class Failed<T>(var error: Throwable) : SocketEvents<T>()
}

//region addons
sealed class SocketCommand(val name: String) {
    object Subscribe : SocketCommand("SUBSCRIBE")
    object Unsubscribe : SocketCommand("UNSUBSCRIBE")
}


data class SocketRequest(
    @SerializedName("id") val id: Int = 1,
    @SerializedName("method") val method: String = "",
    @SerializedName("params") val params: String = ""
)
//end region


interface NetClient<T> {
    var listener: SocketListener<T>
    suspend fun connect(url: String)
    suspend fun cancel()
}

interface SocketListener<T> {
    val flow: Flow<SocketEvents<T>>
    fun onClosed(webSocket: WebSocket, code: Int, reason: String)
    fun onOpen(webSocket: WebSocket, response: Response)
    fun onMessage(webSocket: WebSocket, bytes: ByteString)
    fun onMessage(webSocket: WebSocket, text: String)
    fun onClosing(webSocket: WebSocket, code: Int, reason: String)
    fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?)
}

class OrderNetClient(
    override var listener: SocketListener<Order>
) : NetClient<Order> {

    private var socket: WebSocket? = null

    override suspend fun connect(url: String) {
        val request = Request.Builder()
            //TODO:change url
            .url(url)
            .build()
        socket = OkHttpClient().newWebSocket(request, listener as WebSocketListener)
    }

    //TODO: can't unsubscribe from socket. Instead of it just cancel my WebSocket
 /*   override suspend fun sendCommand(command: SocketCommand, params: String) {
        when (command) {
            SocketCommand.Subscribe -> {
                var text =  Gson().toJson(SocketRequest(1, SocketCommand.Subscribe.name,params))
                Timber.d("SEND COMMAND: $text")
                socket?.send(text)
            }

            SocketCommand.Unsubscribe -> {
                var text =
                    Gson().toJson(SocketRequest(1, SocketCommand.Unsubscribe.name, "@depth@1000ms"))
                socket?.send(text)
                Timber.d("SEND COMMAND: $text")
            }
        }
    }*/

    override suspend fun cancel() {
         socket?.cancel()
    }

}

class OrderSocketListener : WebSocketListener(), SocketListener<Order> {
    private var event: SocketEvents<Order> = SocketEvents.Sleep()

    override var flow = flow<SocketEvents<Order>> {
        while (event != SocketEvents.Closed<Order>()) {
            emit(event)
            delay(WEB_SOCKET_SPEED_DELAY)
        }
    }

    var callback: (event: SocketEvents<Order>) -> SocketEvents<Order> = {
        it
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        event = SocketEvents.Closed()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        event = SocketEvents.Opened()
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        val order = Gson().fromJson(bytes.toString(), Order::class.java)
        event = SocketEvents.Emitted(order)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val order = Gson().fromJson(text, Order::class.java)
        event = callback(SocketEvents.Emitted(order))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        event = (SocketEvents.Opened())
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        event = (SocketEvents.Failed(t))
    }
}

interface Repository<T> {
    suspend fun load(params: String): T
    suspend fun cancel()
}

class OrderRepository @Inject constructor(
    private var socketClient: NetClient<Order>,
    private var rest: NetworkClient.Rest.OrderRest,
    @ActivityContext private var context: Context
) : Repository<Flow<SocketEvents<Order>>> {

    var currencies: Pair<Currency, Currency> = Pair(Currency.Bitcoin, Currency.Tether)

    fun getUrl(): String {
        return "${BASE_WEB_SOCKET_URL}${currencies.first.getName(context).toLowerCase(Locale.ROOT)}" +
                "${currencies.second.getName(context).toLowerCase(Locale.ROOT)}@depth@1000ms"
    }

    override suspend fun load(params: String): Flow<SocketEvents<Order>> {
        socketClient.connect(getUrl())
        var snapshot = rest.load(currencies)

        return socketClient
            .listener
            .flow
            .filter {
                if (it is SocketEvents.Emitted) {
                    it.data.lastUpdateId > snapshot.lastUpdateId + 1
                } else {
                    false
                }
            }
    }


    override suspend fun cancel() {
        socketClient.cancel()
    }


}
