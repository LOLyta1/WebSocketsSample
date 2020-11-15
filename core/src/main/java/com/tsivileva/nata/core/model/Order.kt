package com.tsivileva.nata.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    @SerialName("e")    var e: String = "",
    @SerialName("E")    var eventTime: String ,
    @SerialName("s")    var symbol: String ,
    @SerialName("U")    var firstUpdateId: Long = 0,
    @SerialName("u")    var lastUpdateId: Long = 0,
    @SerialName("b")    var bids: List<List<String>> = listOf(),
    @SerialName("a")    var ask: List<List<String>> = listOf()
)