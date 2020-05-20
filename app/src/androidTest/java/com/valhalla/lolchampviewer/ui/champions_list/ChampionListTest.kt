package com.valhalla.lolchampviewer.ui.champions_list

import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.ViewAssertion
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import com.valhalla.lolchampviewer.repository.EmptyChampionsRepository
import com.valhalla.lolchampviewer.repository.JsonChampionRepository
import com.valhalla.lolchampviewer.ui.MainWizard
import com.valhalla.lolchampviewer.ui.Wizard
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.declareModule


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : KoinTest {

    @get:Rule
    val koinRule = KoinTestRule.create {
        modules(vmModule, wizardModule)
    }

    val vmModule = module {
        viewModel { ChampionListViewModel(get(), get()) }
    }

    val wizardModule = module {
        single { MainWizard() }
    }

    val resources: Resources
        get() = InstrumentationRegistry.getInstrumentation().context.resources

    @Test
    fun test_EmptyList() {
        declareModule {
            single<ChampionsRepository> { EmptyChampionsRepository() }
        }

        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)
        onScreen<ChampionsListScreen> {
            isErrorShown("The champion list is empty")
        }
    }

    @Test
    fun test_ErrorOnLoading() {
        declareModule {
            single<ChampionsRepository> {
                object : ChampionsRepository {
                    override suspend fun getChampions(): List<ChampionShort> {
                        throw Exception("Bad condition happened")
                    }

                    override suspend fun getChampion(championId: String): Champion? = null
                }
            }
        }

        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionsListScreen> {
            isErrorShown("Bad condition happened")
        }
    }

    @Test
    fun test_ItemsLoaded() {
        declareModule {
            single<ChampionsRepository> {
                JsonChampionRepository(resources)
            }
        }

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

//    @Test: todo investigate how to use mockk for tests
    fun test_OpenItem() {
        val wizard = mockk<Wizard>(relaxUnitFun = true)

        declareModule {
            single<ChampionsRepository> {
                JsonChampionRepository(resources)
            }
            single { wizard }
        }

        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionsListScreen> {
            list.firstChild<ChampionItem> { click() }
            view.check(ViewAssertion { view, noFoundException ->
                verify { wizard.openChampionDetails(any()) }
            })
        }
    }
}