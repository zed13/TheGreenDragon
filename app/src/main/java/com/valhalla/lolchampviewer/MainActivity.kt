package com.valhalla.lolchampviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valhalla.lolchampviewer.ui.Wizard
import com.valhalla.lolchampviewer.ui.champion_details.ChampionDetailsViewModel
import com.valhalla.lolchampviewer.ui.champion_history.ChampionHistoryViewModel
import com.valhalla.lolchampviewer.ui.champion_search.ChampionSearchViewModel
import com.valhalla.lolchampviewer.ui.champion_skills.ChampionSkillsViewModel
import com.valhalla.lolchampviewer.ui.champions_list.ChampionListFragment
import com.valhalla.lolchampviewer.ui.champions_list.ChampionListViewModel
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    init {
        getKoin().loadModules(listOf(
            module {
                viewModel<ChampionListViewModel> { ChampionListViewModel(get(), get()) }
                viewModel<ChampionDetailsViewModel> { ChampionDetailsViewModel(get()) }
                viewModel<ChampionHistoryViewModel> { ChampionHistoryViewModel() }
                viewModel<ChampionSkillsViewModel> { ChampionSkillsViewModel() }
                viewModel<ChampionSearchViewModel> { ChampionSearchViewModel(get(), get()) }
            }
        ))
    }

    private val wizard: Wizard by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wizard.attach(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ChampionListFragment(), "list")
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wizard.detach()
    }
}
