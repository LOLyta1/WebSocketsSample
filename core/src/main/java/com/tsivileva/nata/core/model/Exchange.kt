package com.tsivileva.nata.core.model

import android.content.Context
import com.tsivileva.nata.core.R

sealed class ExchangeType() {
    object Ask : ExchangeType()
    object Bid : ExchangeType()
}

data class Exchange(
    var exchangeType: ExchangeType = ExchangeType.Ask,
    var exchangeSymbol: String = "",
    var ordersData: List<Data> = emptyList()
) {
    data class Data(
        var amount: Float = 0f,
        var price: Float = 0f
    ) {
        fun getTotal() = price * amount
    }
}

sealed class Currency(private val nameResourceId: Int) {
    fun getName(context: Context): String {
        return context.resources.getString(nameResourceId)
    }

    object Bitcoin : Currency(R.string.bitCoin)
    object BinanceCoin : Currency(R.string.binanceCoin)
    object Ethereum : Currency(R.string.ethereum)
    object Tether : Currency(R.string.tether)
}


data class Statistic(
    var data: MutableList<Data> = mutableListOf()
) {
    data class Data(
        var bidPrice: Float = 0f,
        var bidAmount: Float = 0f,
        var askPrice: Float = 0f,
        var askAmount: Float = 0f
    ) {
        fun getDiff() = bidAmount * bidPrice - askAmount * askPrice
    }

}