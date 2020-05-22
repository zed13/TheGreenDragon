package com.valhalla.lolchampviewer.tools

import android.content.Context

interface TestResources {

    fun readTextAsset(androidContext: Context, name: String): String {
        val contextClass = this::class.java
        val basePackage = contextClass.getPackage()?.name
        val packagePath = basePackage?.replace(".", "/")
            ?: error("Provided class '$contextClass' should be in package")
        return String(androidContext.assets.open("$packagePath/$name").readBytes())
    }
}

