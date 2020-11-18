package com.tsivileva.nata.network.socket

import com.google.gson.Gson
import com.tsivileva.nata.core.SocketEvents
import com.tsivileva.nata.core.SocketListener
import com.tsivileva.nata.core.WEB_SOCKET_SPEED_DELAY
import com.tsivileva.nata.core.model.dto.Order
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import timber.log.Timber

class SocketListenerImpl : WebSocketListener(), SocketListener<Order> {
    private var event: SocketEvents<Order> = SocketEvents.Sleep()

    override var flow: Flow<SocketEvents<Order>>? = initFlow()
        set(value) {
            field = value ?: initFlow()
        }

    private fun initFlow(): Flow<SocketEvents<Order>>? {
        return flow {
            while (event != SocketEvents.Closed<Order>() && event != SocketEvents.Failed<Order>(null)) {
                emit(event)
                delay(WEB_SOCKET_SPEED_DELAY)
            }
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        event = SocketEvents.Closed()
        Timber.d("$event")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        event = SocketEvents.Opened()
        Timber.d("$event")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        val order = Gson().fromJson(bytes.toString(), Order::class.java)
        event = SocketEvents.Emitted(order)
        Timber.d("$event")

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val order = Gson().fromJson(text, Order::class.java)
        event = SocketEvents.Emitted(order)
        Timber.d("$event")

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        event = SocketEvents.Opened()
        Timber.d("$event")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        event = SocketEvents.Failed(t)
        Timber.d("$event")
    }
}
