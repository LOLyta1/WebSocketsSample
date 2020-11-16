package com.tsivileva.nata.core

import androidx.lifecycle.LiveData
import com.tsivileva.nata.core.webSocket.entity.WebSocketCommand
import com.tsivileva.nata.core.webSocket.entity.ConnectionStatus
import com.tsivileva.nata.core.webSocket.entity.SocketRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface NetworkClient {
    interface WebSocket<T> {
        fun createRequest(command: WebSocketCommand, params: List<String>): SocketRequest
        fun connect()
        fun close()
        fun getStream(): Flow<T>
        fun subscribeOnConnectionStatus(scope: CoroutineScope): LiveData<ConnectionStatus>
        var isConnected: Boolean
    }

    interface Rest<T> {
        suspend fun getData(): T
    }
}