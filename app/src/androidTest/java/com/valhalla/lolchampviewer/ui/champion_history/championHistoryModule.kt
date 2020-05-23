package com.valhalla.lolchampviewer.ui.champion_history

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val championHistoryModule = module {
    viewModel<ChampionHistoryViewModel> { ChampionHistoryViewModel() }
}