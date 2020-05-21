package com.valhalla.lolchampviewer.ui.champions_list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import com.valhalla.lolchampviewer.ui.Wizard
import com.valhalla.lolchampviewer.ui.champions_list.pos.ChampionsListScreen
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.declareModule


@RunWith(AndroidJUnit4::class)
class ChampionList_ErrorOnLoadingTest : KoinTest {

    @get:Rule
    val koinRule = KoinTestRule.create {
        declareModule {
            viewModel { ChampionListViewModel(get(), get()) }
            single { mockk<Wizard>() }
            val repo = mockk<ChampionsRepository>()
            // need to throw unchecked exception; checked will be wrapped with UndeclaredThrowableException
            coEvery { repo.getChampions() }.throws(RuntimeException("Bad condition happened"))
            single { repo }
        }
    }

    @Test
    fun test_ErrorOnLoading() {
        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionsListScreen> {
            isErrorShown("Bad condition happened")
        }
    }
}