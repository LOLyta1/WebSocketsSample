package com.tsivileva.nata.exchange.bid

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsivileva.nata.core.R
import com.tsivileva.nata.core.SocketEvents
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.Exchange
import com.tsivileva.nata.core.model.ExchangeType
import com.tsivileva.nata.core.model.NetworkResponse
import com.tsivileva.nata.core.utils.getExchange
import com.tsivileva.nata.exchange.GetOrdersUseCase
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BidViewModel @ViewModelInject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    @ActivityContext private val context: Context
) : ViewModel() {

    val orders = MutableLiveData<NetworkResponse<Exchange>>()

    private var currencies: Pair<Currency, Currency>? = null

    fun load(currencies: Pair<Currency, Currency>) {
        this.currencies = currencies

        viewModelScope.launch {
            orders.postValue(NetworkResponse.Loading())
            getOrdersUseCase(currencies)?.collect {
                when (it) {
                    is SocketEvents.Sleep -> {
                    }
                    is SocketEvents.Failed -> {
                        orders.postValue(NetworkResponse.Error(it.error?.message.toString()))
                    }
                    is SocketEvents.Emitted -> {
                        val bids = it.data.getExchange(ExchangeType.Bid)
                        orders.postValue(NetworkResponse.Successful(bids))
                    }
                    is SocketEvents.Closed -> {
                        val msg = context.getString(R.string.connectionClosed)
                        orders.postValue(NetworkResponse.Error(msg))
                    }
                }

            }
        }
    }

    fun unsubscribe() {
        viewModelScope.launch {
            getOrdersUseCase.unsubscribe()
            getOrdersUseCase.cancel()
        }
    }

}