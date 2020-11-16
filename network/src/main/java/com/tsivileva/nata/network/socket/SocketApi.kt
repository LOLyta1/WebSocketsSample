package com.tsivileva.nata.network.socket

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.core.model.dto.SocketRequest
import kotlinx.coroutines.flow.Flow

interface SocketApi {
    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>

    @Send
    fun sendRequest(socket: SocketRequest)

    @Receive
    fun observeOrdersTicker(): Flow<Order>
}