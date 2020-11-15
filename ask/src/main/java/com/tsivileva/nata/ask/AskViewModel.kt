package com.tsivileva.nata.ask

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.tsivileva.nata.core.model.Order
import com.tsivileva.nata.network.news.BinanceWebSocketListener
import com.tsivileva.nata.network.news.Client
import com.tsivileva.nata.network.news.WebSocketSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class AskViewModel @ViewModelInject constructor(
    private val getAskUseCase: GetAskUseCase
) : ViewModel() {

    val orders = MutableLiveData<String>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun subscribeOnOrderBook() {

    }

    fun subscribeOnAskOrders(): LiveData<String> {
        return WebSocketSession().test(scope)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}