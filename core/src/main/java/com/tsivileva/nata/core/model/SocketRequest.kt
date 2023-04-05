package com.tsivileva.nata.core.model

import com.google.gson.annotations.SerializedName

data class SocketRequest(
    @SerializedName("id") val id: Int = 1,
    @SerializedName("method") val method: String = "",
    @SerializedName("params") val params: List<String> = listOf()
)

sealed class SocketCommand(val name: String) {
    object Subscribe : SocketCommand("SUBSCRIBE")
    object Unsubscribe : SocketCommand("UNSUBSCRIBE")
}

