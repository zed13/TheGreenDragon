package com.valhalla.lolchampviewer.ui.champions_list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.google.common.truth.Truth.assertThat
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.MockWebServerRule
import com.valhalla.lolchampviewer.tools.OkHttp3IdlingResource
import com.valhalla.lolchampviewer.tools.TestResourcesPlugin
import com.valhalla.lolchampviewer.ui.champions_list.pos.ChampionItem
import com.valhalla.lolchampviewer.ui.champions_list.pos.KChampionListScreen
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.declareModule


@RunWith(AndroidJUnit4::class)
class ChampionList_OpenItemTest : KoinTest, TestResourcesPlugin {

    val serverRule = MockWebServerRule()

    val koinRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule
        )
        declareModule {
            single<String>(Qualifiers.serverAddress) { serverRule.url("/") }
            viewModel<ChampionListViewModel> { ChampionListViewModel(get()) }
        }
    }

    @get:Rule
    val chainedRule = RuleChain.outerRule(serverRule)
        .around(koinRule)

    @Test
    fun test_OpenItem() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_graph)

        val idlingResource = OkHttp3IdlingResource.create("network-idler", get())
        IdlingRegistry.getInstance().register(idlingResource)

        with(serverRule.server) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setAssetBody("champions_list.json")
            )

            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setAssetBody("champion.json")
            )
        }

        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme) {
            ChampionListFragment()
        }
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<KChampionListScreen> {
            list.lastChild<ChampionItem> { click() }
        }

        assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.championDetailsFragment)
    }
}