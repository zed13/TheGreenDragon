package com.valhalla.lolchampviewer.tools

import okhttp3.mockwebserver.MockResponse

interface TestResourcesPlugin : InstrumentationTestPlugin {

    fun readTextAsset(name: String): String {
        val contextClass = this::class.java
        val basePackage = contextClass.getPackage()?.name
        val packagePath = basePackage?.replace(".", "/")
            ?: error("Provided class '$contextClass' should be in package")
        return String(androidContext.assets.open("$packagePath/$name").readBytes())
    }

    fun MockResponse.setAssetBody(name: String): MockResponse {
        return setBody(readTextAsset(name))
    }
}

