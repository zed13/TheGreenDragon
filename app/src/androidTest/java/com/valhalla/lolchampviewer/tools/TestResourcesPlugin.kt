package com.valhalla.lolchampviewer.tools

import okhttp3.mockwebserver.MockResponse

interface TestResourcesPlugin : InstrumentationTestPlugin {

    fun readTextAsset(assetName: String): String {
        val contextClass = this::class.java
        val basePackage = contextClass.getPackage()?.name
        val packagePath = basePackage?.replace(".", "/")
            ?: error("Provided class '$contextClass' should be in package")
        return String(androidContext.assets.open("$packagePath/$assetName").readBytes())
    }


    fun MockResponse.setAssetBody(assetName: String): MockResponse {
        return setBody(readTextAsset(assetName))
    }
}
