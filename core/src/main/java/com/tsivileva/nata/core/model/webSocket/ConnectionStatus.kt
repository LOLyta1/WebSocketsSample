package com.tsivileva.nata.core.model.webSocket


sealed class ConnectionStatus {
    object Opened : ConnectionStatus()
    object Closed : ConnectionStatus()
    class Failed(var error: String) : ConnectionStatus()
}