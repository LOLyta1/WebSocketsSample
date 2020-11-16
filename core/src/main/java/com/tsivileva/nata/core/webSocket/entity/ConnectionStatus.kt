package com.tsivileva.nata.core.webSocket.entity


sealed class ConnectionStatus {
    object Opened : ConnectionStatus()
    object Closed : ConnectionStatus()
    class Failed(var error: String) : ConnectionStatus()
    object Loading : ConnectionStatus()
}