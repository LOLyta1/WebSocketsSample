package com.tsivileva.nata.exchange.com.tsivileva.nata.exchange

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class PagerLifecycleObserver(private val owner: LifecycleOwner):LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onEventFired(){
        Timber.d("${owner.javaClass.name} State:${owner.lifecycle.currentState}")
    }
}