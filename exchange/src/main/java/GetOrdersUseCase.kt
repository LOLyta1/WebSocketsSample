package com.tsivileva.nata.exchange

import android.content.Context
import com.tsivileva.nata.core.model.ConnectionStatus
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.dto.Order
import com.tsivileva.nata.network.OrderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
    @ApplicationContext private val context: Context
) {
    private var params = ""

    @FlowPreview
    @InternalCoroutinesApi
    suspend fun sendRequestForSubscribe(
        currencies: Pair<Currency, Currency>
    ) {
        repository.sendCommandToSubscribe(currencies)
        repository.sendCommandToSubscribe(currencies)
        repository.subscribeOnSocketEvent {
            //repository.sendCommandToSubscribe(currencies)
        }
    }

    @InternalCoroutinesApi
    suspend fun getOrder(
        currencies: Pair<Currency, Currency>
    ) :Flow<Order>{
            return repository.getData(currencies)

    }

    fun unsubscribe() {
        repository.unsubscribe()
    }

    /*private var from: Currency? = null
    private var to: Currency? = null

    fun setCurrency(from: Currency, to: Currency) {
        this.from = from
        this.to = to
    }

    private fun isCurrencySet() = from != null && to != null

    var isConnected = webSocketClient.isConnected

    fun connectToServer() {
        if (isCurrencySet()) {
            webSocketClient.setParams(from!!, to!!, ORDERS_PATH, context)
            webSocketClient.getData()
        }
    }

    fun unsubscribe() {
        webSocketClient.close()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun getData(scope: CoroutineScope): LiveData<Order> {
        val liveData = MutableLiveData<Order>()
        scope.launch {

            if (isCurrencySet()) {
                val snapshot =  restClient.loadSnapshot(from!!.getName(context), to!!.getName(context))

                webSocketClient.getStream().map {
                    it.ask = deleteEmpty(it.ask)
                    it.bids = deleteEmpty(it.bids)
                    it
                }.filter {
                    it.lastUpdateId > snapshot.lastUpdateId + 1
                }.collect {
                    Timber.d("lastU = ${it.lastUpdateId}, firstU = ${it.firstUpdateId}")
                    liveData.postValue(it)
                }
            }
        }
        return liveData
    }

    private fun deleteEmpty(list: List<List<String>>): List<List<String>> {
        return list.toSet().filter {
            val beforePoint = it.last().split(".")[0]
            beforePoint.toInt() > 0
        }.toList()
    }

    fun subscribeConnectionStatus(scope: CoroutineScope) =
        webSocketClient.subscribeOnConnectionStatus(scope)*/

}
