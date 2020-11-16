package com.tsivileva.nata.core.model.dto

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("e") var eventType: String = "",
    @SerializedName("E") var eventTime: String = "",
    @SerializedName("s") var symbol: String = "",
    @SerializedName("U") var firstUpdateId: Long = 0,
    @SerializedName("u") var lastUpdateId: Long = 0,
    @SerializedName("b") var bids: List<List<String>> = listOf(),
    @SerializedName("a") var ask: List<List<String>> = listOf()
)