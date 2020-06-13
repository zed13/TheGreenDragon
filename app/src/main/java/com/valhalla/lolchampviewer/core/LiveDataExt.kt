package com.valhalla.lolchampviewer.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <F, S, R> LiveData<F>.combineLatest(other: LiveData<S>, combiner: (F, S) -> R): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) { value1 ->
        val value2 = other.value
        if (value1 != null && value2 != null) {
            result.value = combiner(value1, value2)
        }
    }
    result.addSource(other) { value2 ->
        val value1 = this@combineLatest.value
        if (value1 != null && value2 != null) {
            result.value = combiner(value1, value2)
        }
    }
    return result
}