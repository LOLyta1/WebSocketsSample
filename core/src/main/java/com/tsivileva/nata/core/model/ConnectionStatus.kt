package com.tsivileva.nata.core.model

import java.lang.Exception


sealed class ConnectionStatus {
    object Opened : ConnectionStatus()
    object Emitted : ConnectionStatus()
    object Closed : ConnectionStatus()
    class Failed(var error: Throwable) : ConnectionStatus()
}