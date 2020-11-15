package com.tsivileva.nata.network.news

import okhttp3.Request
import okhttp3.WebSocket
import okio.ByteString
import java.net.Socket

class BinanceSocket:WebSocket{
    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun close(code: Int, reason: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun queueSize(): Long {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun send(text: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun send(bytes: ByteString): Boolean {
        TODO("Not yet implemented")
    }

}