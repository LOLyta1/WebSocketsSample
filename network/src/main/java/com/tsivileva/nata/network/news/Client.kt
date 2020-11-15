package com.tsivileva.nata.network.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.tsivileva.nata.core.model.ConnectionMethods
import com.tsivileva.nata.core.model.ConnectionRequest
import com.tsivileva.nata.core.model.ConnectionStates
import com.tsivileva.nata.network.BuildConfig
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.internal.ws.WebSocketWriter
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.net.*
import javax.net.SocketFactory


class Client {
    operator fun invoke(httpAddress: String) =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
}

class WebSocketSession(

) {
    private var socket: WebSocket? = null
/*
    fun connect(listener:BinanceWebSocketListener){
        WebSocket.Factory { request, listener ->
         val socket =    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).socketFactory(factory).build()
        }.newWebSocket(,listener)
        soc
    }
*/


    /*
       * Create OkHttpClient and set sockets factory for it
       *
       * @param[factory] factory, which will bw generate socket
       * @return [OkHttpClient] instance
       * */
    fun createOkHttpClientWithSocket(
        factory: SocketFactory
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).socketFactory(factory).build()
    }

    fun createRequest(method: String, body: FormBody): Request {
        return Request.Builder().method(method, body).url(BuildConfig.SOCKET_WEB_API_ENDPOINT)
            .build()
    }

    /*
        * Create FormBody with parameters
        *
        * @param[params] the map of key,value pair
        * @return [FormBody] with parameters inside
        * */
    fun createBodyForConnection(params: Map<String, List<String>>): FormBody {
        val bodyBuilder = FormBody.Builder()
        params.forEach {
            bodyBuilder.add(it.key, it.value.toString())
        }
        return bodyBuilder.build()
    }

    fun createSocketAndConnect(
    ): Socket {
        val simpleSocket = Socket()
        val inetAddr = InetAddress.getByName("stream.binance.com")
        val address = InetSocketAddress(inetAddr, 9443)
        simpleSocket.connect(address)
        return simpleSocket
    }

    fun test(scope: CoroutineScope): LiveData<String> {
        val liveData = MutableLiveData<String>()
        scope.launch {
            try {
                val socket = createSocketAndConnect()
                var connectionRequest = Gson().toJson(
                    ConnectionRequest(
                        params = listOf("btcusdt@depth"),
                        method = ConnectionMethods.Connect.name,
                        id = ConnectionStates.Open.constant
                    )
                )
                connectionRequest = "${connectionRequest}\r\n"
                val outputS = socket.getOutputStream()
                outputS.writer().append(connectionRequest).flush()

                val inputS = socket.getInputStream()
                val bytes = inputS.reader().readText()
                liveData.postValue(bytes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return liveData
    }


}