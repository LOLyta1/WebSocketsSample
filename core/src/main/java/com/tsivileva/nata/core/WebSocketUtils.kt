package com.tsivileva.nata.core

import android.content.Context
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.dto.SocketRequest
import com.tsivileva.nata.core.model.dto.WebSocketCommand
import java.util.*

object WebSocketUtils {

    fun createRequest(
        command: WebSocketCommand,
        params: List<String> = emptyList()
    ): SocketRequest {
        val lowerCaseParams = params.map {
            it.toLowerCase(Locale.ROOT)
        }
        return SocketRequest(
            method = command.name,
            params = lowerCaseParams
        )
    }

}

