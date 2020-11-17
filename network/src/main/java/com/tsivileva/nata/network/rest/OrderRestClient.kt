package com.tsivileva.nata.network.rest

import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tsivileva.nata.core.NetworkClient
import com.tsivileva.nata.core.model.dto.OrderSnapshot
import java.lang.IllegalArgumentException
import java.util.*

const val DEFAULT_ORDER_LIMIT = 1000

class OrderRestClient(
    private val api: RestApi
) : NetworkClient.Rest<OrderSnapshot> {

    private var symbol = ""
    private var limit: Int = DEFAULT_ORDER_LIMIT

    suspend fun loadSnapshot(from: String, to: String, limit: Int = DEFAULT_ORDER_LIMIT): OrderSnapshot {
        symbol = "${from}${to}".toUpperCase(Locale.ROOT)
        this.limit = limit
        return getData()
    }

    override suspend fun getData(): OrderSnapshot {
        if(symbol.isNotBlank()){
              return  api.getOrders(symbol, limit)
        }else {
            throw IllegalArgumentException("symbol is empty! please set it to NetworkClient")
        }
    }
}