package com.valhalla.lolchampviewer.ui.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class PublishSubject<T>(private val scope: CoroutineScope) {

    private val channel: BroadcastChannel<T> =
        BroadcastChannel(1)

    private var job: Job? = null

    fun publish(element: T) {
        job?.cancel()
        job = scope.launch {
            channel.send(element)
        }
    }

    fun openSubscription(): ReceiveChannel<T> {
        return channel.openSubscription()
    }

    fun asFlow(): Flow<T> = channel.openSubscription().consumeAsFlow()
}

fun <T> CoroutineScope.publishSubject(): PublishSubject<T> =
    PublishSubject(this)