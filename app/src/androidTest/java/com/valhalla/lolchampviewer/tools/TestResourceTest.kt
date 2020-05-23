package com.valhalla.lolchampviewer.tools

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestResourceTest : TestResourcesPlugin {

    @Test
    fun testAssetsRead() {
        val text = readTextAsset("test_resource")
        assertEquals("test_resource's file content", text)
    }
}