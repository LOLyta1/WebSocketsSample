package com.tsivileva.nata.network.socket

import android.content.Context
import com.google.gson.Gson
import com.tsivileva.nata.core.BASE_WEB_SOCKET_URL
import com.tsivileva.nata.core.BASE_WEB_SOCKET_URL_HOST
import com.tsivileva.nata.core.NetworkClient
import com.tsivileva.nata.core.SocketListener
import com.tsivileva.nata.core.model.SocketRequest
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.network.OrdersWebSocket
import okhttp3.*
import okio.ByteString
import timber.log.Timber

class SocketClientImp(
    override var listener: SocketListener<Order>,
    var context: Context
) : NetworkClient.Socket<Order> {

    private var socket: WebSocket? = null

    override suspend fun connect(url: String) {
/*
        val urlhtt = HttpUrl.Builder().host("stream.binance.com").port(9443).scheme("wss").build()
*/
        val request = Request.Builder()
            .url(BASE_WEB_SOCKET_URL)
            .build()
/*
        socket = WebSocket.Factory { request, listener ->
            OrdersWebSocket().apply { create() }
        }.newWebSocket(request, listener as WebSocketListener)*/
       socket = OkHttpClient().newWebSocket(request, listener as WebSocketListener)
    }

    override suspend fun sendRequest(request: SocketRequest) {
        val commandText = Gson().toJson(request)
        Timber.d("SEND : $commandText")
        socket?.send(commandText)
        val newRequest = Request.Builder().post(
            FormBody.Builder()
                .add("method",request.method)
                .add("params","[${Gson().toJson(request.params)}")
                .build()
        ).build()
         socket?.
        Timber.d("original request : $req")

    }

    override suspend fun cancel() {
        socket?.cancel()
    }

    override suspend fun close() {
        socket?.close(1000, null)
    }

}