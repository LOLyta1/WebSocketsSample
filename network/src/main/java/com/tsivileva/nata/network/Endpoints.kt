package com.tsivileva.nata.network

sealed class Endpoints(val value: String) {
    object OrdersListSocket : Endpoints("@depth")
}