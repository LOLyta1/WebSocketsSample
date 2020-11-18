package com.tsivileva.nata.exchange

import android.content.Context
import com.tsivileva.nata.core.OrderRepository
import com.tsivileva.nata.core.SocketEvents
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.dto.Order
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(currencies: Pair<Currency, Currency>): Flow<SocketEvents<Order>> {
        repository.currencies = currencies
        return repository.load().filter {
            if (it is SocketEvents.Emitted) {
                deleteEmpty(it.data.bids)
                deleteEmpty(it.data.ask)
                true
            }else{
                false
            }
        }
    }

    suspend fun cancel(){
        repository.cancel()
    }

    private fun deleteEmpty(list: List<List<String>>): List<List<String>> {
        return list.toSet().filter {
            val beforePoint = it.last().split(".")[0]
            beforePoint.toInt() > 0
        }.toList()
    }
}
