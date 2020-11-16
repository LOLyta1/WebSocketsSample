package com.tsivileva.nata.core

import com.tsivileva.nata.core.model.Exchange
import com.tsivileva.nata.core.model.ExchangeType
import com.tsivileva.nata.core.model.Statistic
import com.tsivileva.nata.core.model.dto.Order
import java.lang.Exception

fun Order.getStatistic(): Statistic {
    val statistic = Statistic()
    return try {
        val ask = getExchange(ExchangeType.Ask)
        val bid = getExchange(ExchangeType.Bid)
        val count = ask.ordersData.count()
        for (i in 0 until count) {
            statistic.data.add(
                Statistic.Data(
                    bidPrice = bid.ordersData[i].price,
                    bidAmount = bid.ordersData[i].amount,
                    askPrice = ask.ordersData[i].price,
                    askAmount = ask.ordersData[i].amount,
                )
            )
        }
        statistic
    } catch (e: Exception) {
        statistic
    }
}

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

