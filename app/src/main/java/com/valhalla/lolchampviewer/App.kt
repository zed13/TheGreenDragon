package com.valhalla.lolchampviewer

import android.app.Application
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.ui.MainWizard
import com.valhalla.lolchampviewer.ui.Wizard
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
                module {
                    single<Wizard> { MainWizard() }
                }
            )
        }

        Picasso.setSingletonInstance(getKoin().get())
    }
}