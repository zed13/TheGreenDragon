package com.valhalla.lolchampviewer.ui.champions_list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import com.valhalla.lolchampviewer.repository.EmptyChampionsRepository
import com.valhalla.lolchampviewer.ui.Wizard
import com.valhalla.lolchampviewer.ui.champions_list.pos.ChampionsListScreen
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.declareModule


@RunWith(AndroidJUnit4::class)
class ChampionList_EmptyListTest : KoinTest {

    @get:Rule
    val koinRule = KoinTestRule.create {
        declareModule {
            viewModel { ChampionListViewModel(get(), get()) }
            single { mockk<Wizard>() }
            single<ChampionsRepository> { EmptyChampionsRepository() }
        }
    }

    @Test
    fun test_EmptyList() {
        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)
        onScreen<ChampionsListScreen> {
            isErrorShown("The champion list is empty")
        }
    }
}