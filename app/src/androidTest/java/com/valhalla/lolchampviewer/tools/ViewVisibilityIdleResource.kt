package com.valhalla.lolchampviewer.tools

import android.view.View
import androidx.test.espresso.IdlingResource

class ViewVisibilityIdleResource(
    private val view: View,
    private val expectedVisibility: Int = View.VISIBLE
) : IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    private var isIdle: Boolean = false

    override fun getName(): String = ViewVisibilityIdleResource::class.java.simpleName

    override fun isIdleNow(): Boolean {
        isIdle = isIdle || view.visibility == expectedVisibility

        if (isIdle) {
            callback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}