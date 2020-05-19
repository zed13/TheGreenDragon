package com.valhalla.lolchampviewer

import android.content.res.Resources
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import com.valhalla.lolchampviewer.repository.EmptyChampionsRepository
import com.valhalla.lolchampviewer.repository.JsonChampionRepository
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.ViewVisibilityIdleResource
import com.valhalla.lolchampviewer.ui.champions_list.ChampionListFragment
import com.valhalla.lolchampviewer.ui.champions_list.ChampionListViewModel
import com.valhalla.lolchampviewer.ui.champions_list.ListScreen
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : KoinTest {


    val vmModule = module {
        viewModel { ChampionListViewModel(get(), get()) }
    }

    val resources: Resources
        get() = InstrumentationRegistry.getInstrumentation().context.resources

    @Before
    fun setup() {
        unloadKoinModules(repositoriesModule)
        loadKoinModules(vmModule)
    }

    @After
    fun teardown() {
        unloadKoinModules(vmModule)
    }

    @Test
    fun test_EmptyList() {
        val repositoryModule = module {
            single<ChampionsRepository> { EmptyChampionsRepository() }
        }

        loadKoinModules(repositoryModule)

        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ListScreen> {
            isErrorShown("The champion list is empty")
        }

        unloadKoinModules(repositoryModule)
    }

    @Test
    fun test_ErrorOnLoading() {
        val repositoryModule = module {
            single<ChampionsRepository> {
                object : ChampionsRepository {
                    override suspend fun getChampions(): List<ChampionShort> {
                        throw Exception("Bad condition happened")
                    }

                    override suspend fun getChampion(championId: String): Champion? = null
                }
            }
        }
        loadKoinModules(repositoryModule)

        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ListScreen> {
            isErrorShown("Bad condition happened")
        }

        unloadKoinModules(repositoryModule)
    }

    @Test
    fun test_ItemsLoaded() {
        val repositoryModule = module {
            single<ChampionsRepository> {
                JsonChampionRepository(
                    resources,
                    com.valhalla.lolchampviewer.test.R.raw.champion_formatted
                )
            }
        }
        loadKoinModules(repositoryModule)

        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            val listView = it.view?.findViewById<View>(R.id.list)!!
            val idleResource = ViewVisibilityIdleResource(listView)
            IdlingRegistry.getInstance().register(idleResource)

            onScreen<ListScreen> {
                isListShown()
            }

            IdlingRegistry.getInstance().unregister(idleResource)
        }

        unloadKoinModules(repositoryModule)
    }
}

