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
import com.valhalla.lolchampviewer.ui.champions_list.pos.ChampionsListScreen
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.declareModule


@RunWith(AndroidJUnit4::class)
class ChampionList_ErrorOnLoadingTest : KoinTest {

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
    val rulesChain = RuleChain.outerRule(serverRule)
        .around(koinRule)

    @Test
    fun test_ErrorOnLoading() {
        with(serverRule.server) {
            enqueue(
                MockResponse()
                    .setResponseCode(404)
            )
        }

        val scenario =
            launchFragmentInContainer(themeResId = R.style.AppTheme) { ChampionListFragment() }

        scenario.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionsListScreen> {
            // todo: proper error handling
            isErrorShown("HTTP 404 Client Error")
        }
    }
}