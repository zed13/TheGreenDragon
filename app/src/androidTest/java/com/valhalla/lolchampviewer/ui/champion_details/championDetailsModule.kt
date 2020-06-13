package com.valhalla.lolchampviewer.ui.champion_details

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val championDetailsModule = module {
    viewModel<ChampionDetailsViewModel> { ChampionDetailsViewModel() }
}