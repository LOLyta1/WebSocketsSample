package com.tsivileva.nata.network

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.tsivileva.nata.core.BASE_WEB_SOCKET_URL_HOST
import com.tsivileva.nata.core.EmptyParameterException
import com.tsivileva.nata.core.HttpRequestException
import com.tsivileva.nata.core.WEB_SOCKET_PORT
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.SocketCommand
import okhttp3.Request
import okhttp3.WebSocket
import okio.ByteString
import java.net.InetAddress
import java.net.Socket

class OrdersWebSocket : WebSocket {
    var url = String()

    var currencies: Pair<Currency, Currency>? = null
    private var socket: Socket? = null
    private var gson = Gson()
    private val socketLiveData = MutableLiveData<Socket>()

    init {
       /* try {
            var inetAdress = InetAddress.getByName(BASE_WEB_SOCKET_URL_HOST)
            // val addr:SocketAddress = InetSocketAddress(inetAdress, WEB_SOCKET_PORT)
            socket = Socket(inetAdress, WEB_SOCKET_PORT)
            // socketLiveData.postValue(socket)
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
    }

    fun create() {
        try {
            var inetAdress = InetAddress.getByName(BASE_WEB_SOCKET_URL_HOST)
            // val addr:SocketAddress = InetSocketAddress(inetAdress, WEB_SOCKET_PORT)
            socket = Socket(inetAdress, WEB_SOCKET_PORT)
            // socketLiveData.postValue(socket)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun cancel() {
        socket = null
    }

    override fun close(code: Int, reason: String?): Boolean {
        if (currencies == null) {
            throw EmptyParameterException(Currency::class.simpleName.toString())
        }
        return try {
            currencies?.let {
                val params = WebSocketUtils.createOrdersParams(it)
                val request = WebSocketUtils.createRequest(
                    code, SocketCommand.Unsubscribe,
                    listOf(params)
                )
                val paramsJson = gson.toJson(request)
                socket?.getOutputStream()?.apply {
                    writer().write(paramsJson)
                    flush()
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun queueSize(): Long {
        return 1
    }

    override fun request(): Request {
        //Http request are not allowed here
        throw HttpRequestException()
    }

    override fun send(text: String): Boolean {
        return try {
            socket?.getOutputStream()?.run {
                writer().write(text)
                flush()
                true
            }
            false
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun send(bytes: ByteString): Boolean {
        val text = bytes.toString()
        return send(text)
    }


}