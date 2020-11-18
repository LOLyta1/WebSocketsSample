package com.tsivileva.nata.network.rest

import android.content.Context
import com.tsivileva.nata.core.NetworkClient
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.dto.OrderSnapshot
import java.util.*

const val DEFAULT_ORDER_LIMIT = 1000

class OrderRestClient(
    private val api: RestApi,
    private var context: Context
) : NetworkClient.Rest.OrderRest {

    override suspend fun load(currencies: Pair<Currency, Currency>): OrderSnapshot {
        val limit = DEFAULT_ORDER_LIMIT
        val first = currencies.first.getName(context)
        val second = currencies.second.getName(context)
        val symbol = "${first}${second}".toUpperCase(Locale.ROOT)

        return api.getOrders(symbol, limit)

    }
}