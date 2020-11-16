package com.tsivileva.nata.core.model.dto


import com.google.gson.annotations.SerializedName

data class OrderSnapshot(
    @SerializedName("asks")
    val asks: List<List<String>> = listOf(),
    @SerializedName("bids")
    val bids: List<List<String>> = listOf(),
    @SerializedName("lastUpdateId")
    val lastUpdateId: Long = 0
)