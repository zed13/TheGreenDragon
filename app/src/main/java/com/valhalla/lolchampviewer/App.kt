package com.valhalla.lolchampviewer

import android.app.Application
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                repositoriesModule,
                mainModule,
                module {
                    single(Qualifiers.serverAddress) { DataDragonApi.API_ADDRESS }
                }
            )
        }

        Picasso.setSingletonInstance(getKoin().get())
    }
}