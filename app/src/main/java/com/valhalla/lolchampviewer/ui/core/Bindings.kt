package com.valhalla.lolchampviewer.ui.core

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : View> bindView(@IdRes id: Int): FragmentViewBindingProperty<T> =
    FragmentViewBindingProperty(id)

fun Fragment.onClick(@IdRes id: Int, onClick: (View) -> Unit) {
    view?.findViewById<View>(id)?.setOnClickListener(onClick)
}

class FragmentViewBindingProperty<T : View>(
    @IdRes private val viewId: Int
) : ReadOnlyProperty<Fragment, T?> {

    private var view: T? = null
    private var isAssigned: Boolean = false
    private val lifecycleObserver = BindingLifecycleObserver()

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {

        if (isAssigned) {
            return view
        }

        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        view = thisRef.requireView().findViewById(viewId)
        isAssigned = true
        return view
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            view = null
            isAssigned = false
        }
    }

}