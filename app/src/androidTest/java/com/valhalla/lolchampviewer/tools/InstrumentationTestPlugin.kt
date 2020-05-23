package com.valhalla.lolchampviewer.tools

import android.app.Instrumentation
import android.content.Context
import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry

interface InstrumentationTestPlugin {

    val instrumentation: Instrumentation
        get() = InstrumentationRegistry.getInstrumentation()

    val androidContext: Context
        get() = instrumentation.context

    val resources: Resources
        get() = androidContext.resources
}