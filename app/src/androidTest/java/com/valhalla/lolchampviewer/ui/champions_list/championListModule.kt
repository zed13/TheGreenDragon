package com.valhalla.lolchampviewer.ui.champions_list

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val championListModule = module {
    viewModel<ChampionListViewModel> { ChampionListViewModel(get()) }
}