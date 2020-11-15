package com.tsivileva.nata.core.model

import kotlinx.coroutines.flow.Flow
interface WebSocketClient<T> {
    fun observeWebSocketEvent(): Flow<T>
}

interface RestClient<T> {

}