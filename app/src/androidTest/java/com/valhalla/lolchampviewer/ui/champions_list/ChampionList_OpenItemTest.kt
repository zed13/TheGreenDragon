package com.valhalla.lolchampviewer.ui.champions_list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.MockWebServerRule
import com.valhalla.lolchampviewer.tools.OkHttp3IdlingResource
import com.valhalla.lolchampviewer.tools.TestResourcesPlugin
import com.valhalla.lolchampviewer.ui.Wizard
import com.valhalla.lolchampviewer.ui.champions_list.pos.ChampionItem
import com.valhalla.lolchampviewer.ui.champions_list.pos.ChampionsListScreen
import io.mockk.mockk
import io.mockk.verify
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
            viewModel<ChampionListViewModel> { ChampionListViewModel(get(), get()) }
        }
    }

    @get:Rule
    val chainedRule = RuleChain.outerRule(serverRule)
        .around(koinRule)

    @Test
    fun test_OpenItem() {
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

        val wizard = mockk<Wizard>(relaxUnitFun = true)


        declareModule {
            single { wizard }
        }

        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme) {
            ChampionListFragment()
        }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionsListScreen> {
            list.lastChild<ChampionItem> { click() }
        }

        verify { wizard.openChampionDetails(any()) }
    }
}