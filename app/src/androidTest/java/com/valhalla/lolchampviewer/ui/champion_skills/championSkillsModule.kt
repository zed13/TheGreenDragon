package com.valhalla.lolchampviewer.ui.champion_skills

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val championSkillsModule = module {
    viewModel<ChampionSkillsViewModel> { ChampionSkillsViewModel() }
}