package com.tsivileva.nata.websockets.viewPager

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class PagerLifecycleLogger(private val owner: LifecycleOwner) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.d("LIFECYCLE: ${owner.javaClass.name} ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Timber.d("LIFECYCLE: ${owner.javaClass.name} ON_START")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.d("LIFECYCLE: ${owner.javaClass.name} ON_PAUSE")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Timber.d("LIFECYCLE: ${owner.javaClass.name} ON_RESUME")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Timber.d("LIFECYCLE: ${owner.javaClass.name} ON_STOP")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Timber.d("LIFECYCLE: ${owner.javaClass.name} ON_DESTROY")
    }

}