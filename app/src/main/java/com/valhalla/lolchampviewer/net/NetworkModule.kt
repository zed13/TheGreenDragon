package com.valhalla.lolchampviewer.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .build()
    }

    single<DataDragonApi> {
        Retrofit.Builder()
            .baseUrl(DataDragonApi.API_ADDRESS)
            .client(get())
            .addConverterFactory(
                Json(JsonConfiguration(ignoreUnknownKeys = true))
                    .asConverterFactory("application/json".toMediaType())
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(DataDragonApi::class.java)
    }

    single<Picasso> {
        Picasso.Builder(get())
            .downloader(OkHttp3Downloader(get<OkHttpClient>()))
            .build()
    }
}