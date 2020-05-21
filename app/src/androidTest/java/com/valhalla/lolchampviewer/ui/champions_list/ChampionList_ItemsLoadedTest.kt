package com.valhalla.lolchampviewer.ui.champions_list

import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import com.valhalla.lolchampviewer.repository.JsonChampionRepository
import com.valhalla.lolchampviewer.ui.Wizard
import com.valhalla.lolchampviewer.ui.champions_list.pos.ChampionItem
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
class ChampionList_ItemsLoadedTest : KoinTest {

    @get:Rule
    val koinRule = KoinTestRule.create {
        declareModule {
            viewModel { ChampionListViewModel(get(), get()) }
            single { mockk<Wizard>() }
            single<ChampionsRepository> { JsonChampionRepository(resources) }
        }
    }

    private val resources: Resources
        get() = InstrumentationRegistry.getInstrumentation().context.resources

    @Test
    fun test_ItemsLoaded() {
        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionsListScreen> {
            isListShown()

            list.firstChild<ChampionItem> {
                hasChampionName("Aatrox")
                hasChampionTitle("the Darkin Blade")
            }

            list.lastChild<ChampionItem> {
                hasChampionName("Zyra")
                hasChampionTitle("Rise of the Thorns")
            }
        }
    }
}