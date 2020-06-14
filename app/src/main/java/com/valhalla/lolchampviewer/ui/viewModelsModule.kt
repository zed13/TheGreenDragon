package com.valhalla.lolchampviewer.ui

import com.valhalla.lolchampviewer.ui.champion_details.ChampionDetailsViewModel
import com.valhalla.lolchampviewer.ui.champion_history.ChampionHistoryViewModel
import com.valhalla.lolchampviewer.ui.champion_search.ChampionSearchViewModel
import com.valhalla.lolchampviewer.ui.champion_skills.ChampionSkillsViewModel
import com.valhalla.lolchampviewer.ui.champions_list.ChampionListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel<ChampionListViewModel> { ChampionListViewModel(get()) }
    viewModel<ChampionDetailsViewModel> { ChampionDetailsViewModel() }
    viewModel<ChampionHistoryViewModel> { ChampionHistoryViewModel() }
    viewModel<ChampionSkillsViewModel> { ChampionSkillsViewModel() }
    viewModel<ChampionSearchViewModel> { ChampionSearchViewModel(get()) }
}