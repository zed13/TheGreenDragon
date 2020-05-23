package com.valhalla.lolchampviewer.ui.champion_details

import com.valhalla.lolchampviewer.ui.Wizard
import io.mockk.mockk
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val championDetailsModule = module {
    single<Wizard> { mockk() }
    viewModel<ChampionDetailsViewModel> { ChampionDetailsViewModel(get()) }
}