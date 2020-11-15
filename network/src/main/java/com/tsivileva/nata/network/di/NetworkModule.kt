package com.tsivileva.nata.network.di

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.OkHttpClientWebSocketConnectionEstablisher
import com.tinder.scarlet.websocket.okhttp.OkHttpWebSocket
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton
import com.tinder.scarlet.websocket.okhttp.request.RequestFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import com.tsivileva.nata.core.model.Order
import com.tsivileva.nata.core.model.WebSocketClient
import com.tsivileva.nata.network.BinanceService
import com.tsivileva.nata.network.BinanceWebSocketClient
import com.tsivileva.nata.network.BuildConfig
import com.tsivileva.nata.network.FlowStreamAdapter
import dagger.Binds
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        )
        .build()


    @Provides
    @Singleton
    fun provideBinanceService(client: OkHttpClient) = Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory(BuildConfig.SOCKET_WEB_API_ENDPOINT))
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .build()
            .create(BinanceService::class.java)

    @Provides
    @Singleton
    fun provideWebSocketClient(service:BinanceService): BinanceWebSocketClient {
        return  BinanceWebSocketClient(service)
    }
    /*
    @Provides
    fun provideWebSocketClientImpl(
        client: BinanceWebSocketClient
    ): WebSocketClient<Order> {
        return client
    }*/

}/*
@Module
@InstallIn(ApplicationComponent::class)
abstract class BinanceServiceBinding() {
    @Singleton
    @Binds
    abstract fun provideBinanceServiceBinding(
        implementation: BinanceWebSocketClient
    ): NetworkClient.WebSocketClient<Order>*/
//}
