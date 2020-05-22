package com.valhalla.lolchampviewer.tools

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestResourceTest : TestResources {

    val context: Context
        get() = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun testAssetsRead() {
        val text = readTextAsset(context, "test_resource")
        assertEquals("test_resource's file content", text)
    }
}