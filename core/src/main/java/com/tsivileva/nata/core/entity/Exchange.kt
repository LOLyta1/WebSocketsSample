package com.tsivileva.nata.core.entity

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

sealed class ExchangeType() {
    object Ask : ExchangeType()
    object Bid : ExchangeType()
}