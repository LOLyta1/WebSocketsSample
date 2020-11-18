package com.tsivileva.nata.network.socket

import android.content.Context
import com.google.gson.Gson
import com.tsivileva.nata.core.NetworkClient
import com.tsivileva.nata.core.SocketListener
import com.tsivileva.nata.core.model.SocketRequest
import com.tsivileva.nata.core.model.dto.Order
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber
import java.net.Socket

class SocketClientImp(
    override var listener: SocketListener<Order>,
    var context: Context
) : NetworkClient.Socket<Order> {

    private var socket: WebSocket? = null

    override suspend fun connect(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()
        socket = OkHttpClient().newWebSocket(request, listener as WebSocketListener)
    }

    override suspend fun sendRequest(request: SocketRequest) {
        val commandText = Gson().toJson(request)
        Timber.d("SEND : $commandText")
        socket?.send(commandText)
    }

    override suspend fun cancel() {
        socket?.cancel()
    }

    override suspend fun close() {
       socket?.close(1000,null)
    }
}