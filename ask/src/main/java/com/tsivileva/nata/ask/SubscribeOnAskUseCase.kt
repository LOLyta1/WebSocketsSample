package com.tsivileva.nata.ask

import com.tsivileva.nata.network.news.BinanceWebSocketListener
import com.tsivileva.nata.network.news.WebSocketSession

class SubscribeOnAskUseCase(
    private val listener: BinanceWebSocketListener,
    private val session: WebSocketSession
) {
   fun start() {
        session.test(listener)
    }
}