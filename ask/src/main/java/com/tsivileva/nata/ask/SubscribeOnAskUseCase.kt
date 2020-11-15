package com.tsivileva.nata.ask

import com.tsivileva.nata.network.news.BinanceWebSocketListener
import com.tsivileva.nata.network.news.WebSocketSession
import kotlinx.coroutines.CoroutineScope

class SubscribeOnAskUseCase(
    private val listener: BinanceWebSocketListener,
    private val session: WebSocketSession,
    private val scope: CoroutineScope
) {
    fun start() {
        session.test(scope)
    }
}