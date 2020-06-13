package com.valhalla.lolchampviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valhalla.lolchampviewer.ui.champion_details.ChampionDetailsViewModel
import com.valhalla.lolchampviewer.ui.champion_history.ChampionHistoryViewModel
import com.valhalla.lolchampviewer.ui.champion_search.ChampionSearchViewModel
import com.valhalla.lolchampviewer.ui.champion_skills.ChampionSkillsViewModel
import com.valhalla.lolchampviewer.ui.champions_list.ChampionListViewModel
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    init {
        getKoin().loadModules(listOf(
            module {
                viewModel<ChampionListViewModel> { ChampionListViewModel(get()) }
                viewModel<ChampionDetailsViewModel> { ChampionDetailsViewModel() }
                viewModel<ChampionHistoryViewModel> { ChampionHistoryViewModel() }
                viewModel<ChampionSkillsViewModel> { ChampionSkillsViewModel() }
                viewModel<ChampionSearchViewModel> { ChampionSearchViewModel(get()) }
            }
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
