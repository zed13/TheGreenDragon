package com.valhalla.lolchampviewer.ui.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    infix fun <T> Flow<T>.bindTo(collector: (T) -> Unit): Job {
        return lifecycleScope.launch {
            collect { collector(it) }
        }
    }
}