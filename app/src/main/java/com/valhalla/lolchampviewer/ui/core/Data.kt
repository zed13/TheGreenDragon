package com.valhalla.lolchampviewer.ui.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

sealed class Data<T>(val isPresent: Boolean) {

    data class Present<T>(val data: T) : Data<T>(true)
    object Absent : Data<Any>(false)

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun <T> of(value: T?): Data<T> = if (value != null) {
            Present(value)
        } else {
            Absent as Data<T>
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> absent(): Data<T> = Absent as Data<T>
    }
}

fun <T> Flow<Data<T>>.onlyPresent(): Flow<T> = filterIsInstance<Data.Present<T>>().map { it.data }