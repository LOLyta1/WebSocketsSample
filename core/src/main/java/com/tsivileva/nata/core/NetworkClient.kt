package com.tsivileva.nata.core

import androidx.lifecycle.LiveData
import com.tsivileva.nata.core.model.dto.WebSocketCommand
import com.tsivileva.nata.core.model.ConnectionStatus
import com.tsivileva.nata.core.model.dto.SocketRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface NetworkClient {
    interface WebSocket<T> {
        fun subscribeOnSocketEvent(): Flow<com.tinder.scarlet.WebSocket.Event>
        fun sendRequest(socket: SocketRequest)
        fun getData(): Flow<T>
    }

    interface Rest<T> {
        suspend fun getData(): T
    }
}