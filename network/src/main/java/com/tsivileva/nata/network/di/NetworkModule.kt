package com.tsivileva.nata.network.di

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tsivileva.nata.network.socket.BinanceService
import com.tsivileva.nata.network.socket.WebSocketClient
import com.tsivileva.nata.network.BuildConfig
import com.tsivileva.nata.network.socket.FlowStreamAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

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
    fun provideWebSocketClient(service: BinanceService): WebSocketClient {
        return WebSocketClient(service)
    }
}
