package com.tsivileva.nata.network.di

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tsivileva.nata.network.socket.SocketApi
import com.tsivileva.nata.network.socket.OrderWebSocketClient
import com.tsivileva.nata.network.BuildConfig
import com.tsivileva.nata.network.rest.OrderRestClient
import com.tsivileva.nata.network.rest.RestApi
import com.tsivileva.nata.network.socket.adapter.FlowStreamAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideSocketService(client: OkHttpClient) = Scarlet.Builder()
        .webSocketFactory(client.newWebSocketFactory(BuildConfig.SOCKET_WEB_API_ENDPOINT))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(FlowStreamAdapter.Factory)
        .build()
        .create(SocketApi::class.java)

    @Provides
    @Singleton
    fun provideWebSocketClient(api: SocketApi): OrderWebSocketClient {
        return OrderWebSocketClient(api)
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BuildConfig.REST_API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(RestApi::class.java)

    @Provides
    @Singleton
    fun provideRestClient(api: RestApi): OrderRestClient {
        return OrderRestClient(api)
    }
}
