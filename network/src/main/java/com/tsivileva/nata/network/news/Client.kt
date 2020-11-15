package com.tsivileva.nata.network.news

import com.google.gson.Gson
import com.tsivileva.nata.core.model.ConnectionMethods
import com.tsivileva.nata.core.model.ConnectionRequest
import com.tsivileva.nata.core.model.ConnectionStates
import com.tsivileva.nata.network.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
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

    fun connect(
        connectionRequest: ConnectionRequest,
        client: OkHttpClient,
        listener: BinanceWebSocketListener
    ): Boolean {
        val request = Request.Builder().url(BuildConfig.SOCKET_WEB_API_ENDPOINT).build()
        socket = client.newWebSocket(request, listener)
        return socket!!.send(Gson().toJson(connectionRequest))
    }


    fun test(listener: BinanceWebSocketListener) {
        val factory = BinanceSocketFacoty.createSocketFactory()
        val clientWithSocket = createOkHttpClientWithSocket(factory)
        val params = ConnectionRequest(
            id = ConnectionStates.Connecting.constant,
            method = ConnectionMethods.Connect.name,
            params = listOf("btcusdt@depth")
        )
        connect(params, clientWithSocket, listener)
    }
}