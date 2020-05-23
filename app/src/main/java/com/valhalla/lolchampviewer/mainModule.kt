package com.valhalla.lolchampviewer

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.dsl.module

val mainModule = module {
    single<Json> { Json(JsonConfiguration(ignoreUnknownKeys = true)) }
}