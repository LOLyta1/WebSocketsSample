package com.tsivileva.nata.network.socket

import com.google.gson.Gson
import com.tsivileva.nata.core.SocketEvents
import com.tsivileva.nata.core.SocketListener
import com.tsivileva.nata.core.WEB_SOCKET_SPEED_DELAY
import com.tsivileva.nata.core.model.dto.Order
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class SocketListenerImpl : WebSocketListener(), SocketListener<Order> {
    private var event: SocketEvents<Order> = SocketEvents.Sleep()

    override var flow = flow {
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
