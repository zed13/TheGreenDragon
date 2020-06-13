package com.valhalla.lolchampviewer.core

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.Closeable

class Publisher<T> : Closeable {

    private val channel: BroadcastChannel<T> = BroadcastChannel(1)

    private val subscriptions: MutableList<ReceiveChannel<T>> = ArrayList()

    private val lifecycleObserver = PublisherLifecycleObserver()

    val events: Flow<T>
        get() {
            val subscription = channel.openSubscription()
            subscriptions.add(subscription)
            return subscription.receiveAsFlow()
        }

    suspend fun publish(event: T) {
        channel.send(event)
    }

    inner class PublisherLifecycleObserver : DefaultLifecycleObserver {
        override fun onPause(owner: LifecycleOwner) {
            subscriptions.forEach { it.cancel() }
            subscriptions.clear()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
        }
    }

    /**
     * Clean up when [ViewModel.clear] method is called
     */
    override fun close() {
        channel.cancel()
    }

    fun registerOn(lifecycle: Lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
    }
}


