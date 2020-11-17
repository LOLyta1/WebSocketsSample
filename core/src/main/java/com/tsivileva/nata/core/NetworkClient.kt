package com.tsivileva.nata.core

import androidx.lifecycle.LiveData
import com.tsivileva.nata.core.model.dto.WebSocketCommand
import com.tsivileva.nata.core.model.ConnectionStatus
import com.tsivileva.nata.core.model.dto.SocketRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface NetworkClient {
    interface WebSocket<T> {
/*        fun createRequest(command: WebSocketCommand, params: List<String>): SocketRequest
        fun connect()
        fun close()
        fun getStream(): Flow<T>
        fun subscribeOnConnectionStatus(scope: CoroutineScope): LiveData<ConnectionStatus>
        var isConnected: Boolean*/
        fun subscribeOnSocketEvent(): Flow<com.tinder.scarlet.WebSocket.Event>
        fun sendRequest(request: SocketRequest)
        fun getData(): Flow<T>
    }

    interface Rest<T> {
        suspend fun getData(): T
    }
}