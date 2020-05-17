package com.valhalla.lolchampviewer.repository

import org.koin.dsl.module

val repositoriesModule = module {
    single<ChampionsRepository> { DragonChampionsRepository(get()) }
}