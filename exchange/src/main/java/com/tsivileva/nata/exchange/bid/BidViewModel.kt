package com.tsivileva.nata.exchange.bid

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tsivileva.nata.core.R
import com.tsivileva.nata.core.SocketEvents
import com.tsivileva.nata.core.getExchange
import com.tsivileva.nata.core.model.*
import com.tsivileva.nata.exchange.GetOrdersUseCase
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

class BidViewModel @ViewModelInject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    @ActivityContext private val context: Context
) : ViewModel() {

    val orders = MutableLiveData<NetworkResponse<Exchange>>()

    fun load(currencies: Pair<Currency, Currency>) {
        viewModelScope.launch {
            orders.postValue(NetworkResponse.Loading())
            getOrdersUseCase(currencies).collect {
                Timber.d("loaded new item ${it.toString()}")
                when(it){
                    is SocketEvents.Sleep ->{
                        Timber.d("sleep")
                    }
                    is SocketEvents.Failed ->{
                        orders.postValue(NetworkResponse.Error(it.error.message.toString()))
                    }
                    is SocketEvents.Emitted -> {
                        val bids = it.data.getExchange(ExchangeType.Bid)
                        Timber.d("BID ${it.data.symbol}")
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

    fun cancel(){
        viewModelScope.launch {
            getOrdersUseCase.cancel()
        }
    }

}