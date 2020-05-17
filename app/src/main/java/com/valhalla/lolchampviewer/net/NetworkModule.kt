package com.valhalla.lolchampviewer.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single<DataDragonApi> {
        Retrofit.Builder()
            .baseUrl("""http:\\192.168.0.51:8080""")
            .addConverterFactory(
                Json(JsonConfiguration(ignoreUnknownKeys = true))
                    .asConverterFactory(MediaType.parse("application/json")!!)
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(DataDragonApi::class.java)
    }
}