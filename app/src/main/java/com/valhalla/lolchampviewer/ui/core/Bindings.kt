package com.valhalla.lolchampviewer.ui.core

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun <T : View> Fragment.bindView(@IdRes id: Int): Lazy<T?> = lazy { view?.findViewById<T>(id) }

fun Fragment.onClick(@IdRes id: Int, onClick: (View) -> Unit) {
    view?.findViewById<View>(id)?.setOnClickListener(onClick)
}