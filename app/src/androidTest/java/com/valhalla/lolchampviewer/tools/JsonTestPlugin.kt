package com.valhalla.lolchampviewer.tools

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

interface JsonTestPlugin {

    val json: Json
        get() = Json(JsonConfiguration.Default)

    fun <T> TestResourcesPlugin.readEntity(assetName: String, deserializer: DeserializationStrategy<T>): T {
        val textData = readTextAsset(assetName)
        return json.parse(deserializer, textData)
    }
}