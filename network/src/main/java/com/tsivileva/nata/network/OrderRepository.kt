package com.tsivileva.nata.network

import android.content.Context
import com.tsivileva.nata.core.*
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.SocketCommand
import com.tsivileva.nata.core.model.SocketRequest
import com.tsivileva.nata.core.model.dto.Order
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class OrderRepository @Inject constructor(
    private var socketClient: NetworkClient.Socket<Order>,
    private var rest: NetworkClient.Rest.OrderRest,
    @ActivityContext private var context: Context
) : Repository<Flow<SocketEvents<Order>>> {

    var currencies: Pair<Currency, Currency> = Pair(Currency.Bitcoin, Currency.Tether)
    private var connectionId: Int = 0

    override suspend fun load(): Flow<SocketEvents<Order>>? {
        socketClient.connect(getUrl())

        val snapshot = rest.load(currencies)

        return socketClient
            .listener
            .flow
            ?.filter {
                if (it is SocketEvents.Emitted) {
                    it.data.lastUpdateId > snapshot.lastUpdateId + 1
                } else {
                    false
                }
            }
    }

    private fun getUrl(): String {
        return "$BASE_WEB_SOCKET_URL${
            currencies.first.getName(context).toLowerCase(Locale.ROOT)
        }" +
                "${
                    currencies.second.getName(context).toLowerCase(Locale.ROOT)
                }$WEB_SOCKET_ORDERS_ENDPOINT_PATH"
    }

    override suspend fun cancel() {
        socketClient.cancel()
    }

    override suspend fun subscribe() {
        val params = createOrdersParams(currencies, context)
        val request = createRequest(SocketCommand.Subscribe, listOf(params))
        socketClient.sendRequest(request)
    }

    override suspend fun unsubscribe() {
        val params = createOrdersParams(currencies, context)
        val request = createRequest(SocketCommand.Unsubscribe, listOf(params))
        socketClient.sendRequest(request)
    }

    private fun createOrdersParams(
        currencies: Pair<Currency, Currency>,
        context: Context
    ): String {
        return currencies.first.getName(context) +
                currencies.second.getName(context) +
                WEB_SOCKET_ORDERS_ENDPOINT_PATH
    }

    private fun createRequest(
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

    override suspend fun close() {
        generateNewConnectionId()
        socketClient.listener.flow = null
    }

    private fun generateNewConnectionId() {
        var newId = 0
        while (newId == connectionId) {
            newId = Random.nextInt(1, 100)
        }
        connectionId = newId
    }


}
