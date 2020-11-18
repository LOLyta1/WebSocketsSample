package com.tsivileva.nata.network

import com.tsivileva.nata.core.WEB_SOCKET_ORDERS_ENDPOINT_PATH
import com.tsivileva.nata.core.model.SocketCommand
import com.tsivileva.nata.core.model.SocketRequest
import java.util.*

object WebSocketUtils {

    fun createRequest(
        connectionId: Int,
        command: SocketCommand,
        params: List<String> = emptyList()
    ): SocketRequest {
        val lowerCaseParams = params.map {
            it.toLowerCase(Locale.ROOT)
        }

        return SocketRequest(
            id = connectionId,
            method = command.name,
            params = lowerCaseParams
        )
    }

    fun createOrdersParams(
        currencies: Pair<com.tsivileva.nata.core.model.Currency, com.tsivileva.nata.core.model.Currency>
    ): String {
        return currencies.first.symbol +
                currencies.second.symbol +
                WEB_SOCKET_ORDERS_ENDPOINT_PATH
    }
}
/*

private fun generateNewConnectionId() {
    var newId = 0
    while (newId == connectionId) {
        newId = Random.nextInt(1, 100)
    }
    connectionId = newId
}*/
