package com.tsivileva.nata.core.model

sealed class ConnectionStates(val constant:Int) {
    object Connecting : ConnectionStates(0)
    object Open: ConnectionStates(1)
    object Closing:ConnectionStates(2)
    object Closed:ConnectionStates(3)
}