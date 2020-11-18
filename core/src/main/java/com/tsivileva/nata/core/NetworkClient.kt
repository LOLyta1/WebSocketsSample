package com.tsivileva.nata.core

import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.SocketRequest
import com.tsivileva.nata.core.model.dto.OrderSnapshot
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString


interface NetworkClient {
    interface Socket<T> {
        var listener: SocketListener<T>
        suspend fun connect(url: String)
        suspend fun sendRequest(request: SocketRequest)
        suspend fun cancel()
        suspend fun close()
    }

    interface Rest {
        interface OrderRest {
            suspend fun load(currencies: Pair<Currency, Currency>): OrderSnapshot
        }
    }
}

interface SocketListener<T> {
    var flow: Flow<SocketEvents<T>>?
    fun onClosed(webSocket: WebSocket, code: Int, reason: String)
    fun onOpen(webSocket: WebSocket, response: Response)
    fun onMessage(webSocket: WebSocket, bytes: ByteString)
    fun onMessage(webSocket: WebSocket, text: String)
    fun onClosing(webSocket: WebSocket, code: Int, reason: String)
    fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?)
}

sealed class SocketEvents<T> {
    class Sleep<T> : SocketEvents<T>()
    class Opened<T> : SocketEvents<T>()
    class Emitted<T>(var data: T) : SocketEvents<T>()
    class Closed<T> : SocketEvents<T>()
    class Failed<T>(var error: Throwable?) : SocketEvents<T>()
}

