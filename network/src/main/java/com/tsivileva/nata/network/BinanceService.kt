package com.tsivileva.nata.network

import androidx.lifecycle.LiveData
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.tsivileva.nata.core.model.Order
import kotlinx.coroutines.flow.Flow

interface BinanceService {
    @Send
    fun sendSubscribe(any: Any)

    @Receive
    fun observeOrdersTicker(): Flow<Order>
}