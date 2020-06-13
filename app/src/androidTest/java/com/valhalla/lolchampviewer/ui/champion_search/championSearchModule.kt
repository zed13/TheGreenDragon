package com.valhalla.lolchampviewer.ui.champion_search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val championSearchModule = module {
    viewModel<ChampionSearchViewModel> { ChampionSearchViewModel(get()) }
}