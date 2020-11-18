package com.tsivileva.nata.network.di

import android.content.Context
import com.tsivileva.nata.core.NetworkClient
import com.tsivileva.nata.core.SocketListener
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.network.BuildConfig
import com.tsivileva.nata.network.rest.OrderRestClient
import com.tsivileva.nata.network.rest.RestApi
import com.tsivileva.nata.network.socket.SocketClientImp
import com.tsivileva.nata.network.socket.SocketListenerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideSocketListener(): SocketListener<Order> {
        return SocketListenerImpl()
    }

    @Provides
    @Singleton
    fun provideSocketClient(
        listener: SocketListener<Order>,
        @ApplicationContext context: Context
    ): NetworkClient.Socket<Order> {
        return SocketClientImp(listener, context)
    }


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
        return OrderRestClient(api, context)
    }
}
