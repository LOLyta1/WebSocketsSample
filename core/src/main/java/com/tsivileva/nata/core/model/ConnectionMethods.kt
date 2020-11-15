package com.tsivileva.nata.core.model

sealed class ConnectionMethods(val name: String) {
    object Connect : ConnectionMethods("SUBSCRIBE")
    object Close : ConnectionMethods("UNSUBSCRIBE")
}