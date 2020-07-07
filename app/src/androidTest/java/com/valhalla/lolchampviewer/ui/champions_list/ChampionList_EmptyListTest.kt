package com.valhalla.lolchampviewer.ui.champions_list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.MockWebServerRule
import com.valhalla.lolchampviewer.tools.TestResourcesPlugin
import com.valhalla.lolchampviewer.ui.champions_list.pos.KChampionListScreen
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.declareModule


@RunWith(AndroidJUnit4::class)
class ChampionList_EmptyListTest : KoinTest, TestResourcesPlugin {

    val serverRule = MockWebServerRule()

    val koinRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule,
            championListModule
        )

        declareModule {
            single<String>(Qualifiers.serverAddress) { serverRule.url("/") }
        }
    }

    @get:Rule
    val chain = RuleChain.outerRule(serverRule)
        .around(koinRule)

    @Test
    fun test_EmptyList() {
        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        with(serverRule.server) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setAssetBody("empty_champions_list.json")
            )
        }

        scenario.moveToState(Lifecycle.State.RESUMED)
        onScreen<KChampionListScreen> {
            isErrorShown("The champion list is empty")
        }
    }
}