package com.tsivileva.nata.ask

import com.tsivileva.nata.core.model.Order
import com.tsivileva.nata.core.model.WebSocketClient
import com.tsivileva.nata.network.BinanceWebSocketClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

class GetAskUseCase @Inject constructor(
    private val networkClient: BinanceWebSocketClient
) {

    operator fun invoke() = networkClient.observeWebSocketEvent()
}
