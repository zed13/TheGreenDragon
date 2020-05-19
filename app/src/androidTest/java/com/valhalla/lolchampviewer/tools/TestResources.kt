package com.valhalla.lolchampviewer.tools

import java.io.File
import java.io.FileNotFoundException

object TestResources {

    fun readFile(context: Any, name: String): String {
        return readFile(context::class.java, name)
    }

    fun readFile(contextClass: Class<*>, name: String): String {
        val basePackage = contextClass.getPackage()?.name
        val packagePath = basePackage?.replace(".", "/")
                ?: error("Provided class '$contextClass' should be in package")
        val resourcesFile =
                contextClass.classLoader?.getResource(File(packagePath, name).path)
                        ?.let { File(it.toURI()) }
        if (resourcesFile?.exists() == true) {
            return resourcesFile.readText()
        } else {
            throw FileNotFoundException("Couldn't found file '$name' withing package '$basePackage' in resources")
        }
    }
}