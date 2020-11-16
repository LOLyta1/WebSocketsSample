package com.tsivileva.nata.core.model


data class Statistic(
    var data: MutableList<Data> = mutableListOf()
) {
    data class Data(
        var bidPrice: Float = 0f,
        var bidAmount: Float = 0f,
        var askPrice: Float = 0f,
        var askAmount: Float = 0f
    ){
        fun getDiff() = bidAmount*bidPrice - askAmount*askPrice
    }

}