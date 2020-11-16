package com.tsivileva.nata.core

import com.tsivileva.nata.core.entity.Exchange
import com.tsivileva.nata.core.entity.ExchangeType
import com.tsivileva.nata.core.model.Order
import java.lang.Exception


fun Order.getExchange(type: ExchangeType): Exchange {
    return try {
        val exchangeData = Exchange.Data()
        val allExchangeData = mutableListOf<Exchange.Data>()
        val list = when (type) {
            is ExchangeType.Ask -> this.ask
            is ExchangeType.Bid -> this.bids
        }
        list.forEach {
            exchangeData.price = it.first().toFloat()
            exchangeData.amount = it.last().toFloat()
            allExchangeData.add(exchangeData)
        }
        Exchange(
            exchangeType = type,
            exchangeSymbol = this.symbol,
            ordersData = allExchangeData.distinct()
        )
    } catch (e: Exception) {
        e.printStackTrace()
        Exchange()
    }
}