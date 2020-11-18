package com.tsivileva.nata.network.di

import android.content.Context
import com.tsivileva.nata.core.*
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.core.model.dto.OrderSnapshot
import com.tsivileva.nata.network.BuildConfig
import com.tsivileva.nata.network.rest.OrderRestClient
import com.tsivileva.nata.network.rest.RestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

/*
    @Provides
    @Singleton
    fun provideSocketService(client: OkHttpClient) = Scarlet.Builder()
        .webSocketFactory(client.newWebSocketFactory(BuildConfig.SOCKET_WEB_API_ENDPOINT))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(FlowStreamAdapter.Factory)
        .build()
        .create(SocketApi::class.java)*/
/*

    //TODO:delete this
    @Provides
    fun provideWebSocketClient(api: SocketApi): OrderWebSocketClient {
        return OrderWebSocketClient(api)
    }
*/


    @Provides
    @Singleton
    fun provideSocketListener(): SocketListener<Order> {
        return OrderSocketListener()
    }

    @Provides
    @Singleton
    fun provideSocketClient(listener: SocketListener<Order>): NetClient<Order> {
        return OrderNetClient(listener)
    }

    /* @Provides
     @Singleton
     fun provideRepository(
         netClient:NetClient<Order>,
         restClient: OrderRestClient,
         @ActivityContext context: Context
     ):Repository<Flow<SocketEvents<Order>>> {
        return  OrderRepository(netClient,restClient,context)
     }*/


    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        )
        .build()

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
    fun provideRestClient(
        api: RestApi,
        @ApplicationContext context: Context
    ): NetworkClient.Rest.OrderRest {
        return OrderRestClient(api,context)
    }
}
