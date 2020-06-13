package com.valhalla.lolchampviewer.core

import androidx.test.espresso.idling.CountingIdlingResource

fun <T> CountingIdlingResource.perform(operation: () -> T): T {
    increment()
    val result = operation()
    decrement()
    return result
}