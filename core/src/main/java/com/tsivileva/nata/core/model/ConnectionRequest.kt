package com.tsivileva.nata.core.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectionRequest(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("method")
    val method: String = "",
    @SerialName("params")
    val params: List<String> = listOf()
)