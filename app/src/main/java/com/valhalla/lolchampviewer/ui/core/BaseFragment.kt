package com.valhalla.lolchampviewer.ui.core

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    infix fun <T> LiveData<T>.bindTo(handler: (T) -> Unit) {
        observe(this@BaseFragment, Observer { it?.let(handler) })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBack()
                }
            })
    }

    open fun onBack() {
        if (!findNavController().popBackStack()) {
            requireActivity().finish()
        }
    }
}