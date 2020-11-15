package com.tsivileva.nata.network

import com.tsivileva.nata.core.model.Order
import com.tsivileva.nata.core.model.WebSocketClient
import kotlinx.coroutines.flow.Flow
class BinanceWebSocketClient (
    var service: BinanceService
) : WebSocketClient<Order> {

    override fun observeWebSocketEvent(): Flow<Order> {
        return service.observeOrdersTicker()
    }
}