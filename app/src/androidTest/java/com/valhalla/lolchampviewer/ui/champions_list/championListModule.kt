package com.valhalla.lolchampviewer.ui.champions_list

import com.valhalla.lolchampviewer.ui.Wizard
import io.mockk.mockk
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val championListModule = module {
    single<Wizard> { mockk() }
    viewModel<ChampionListViewModel> { ChampionListViewModel(get(), get()) }
}