package com.valhalla.lolchampviewer.ui.champion_search

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.*
import com.valhalla.lolchampviewer.ui.champion_search.pos.KSearchScreen
import com.valhalla.lolchampviewer.ui.champion_search.pos.SearchItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.allOf
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.declareModule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ChampionSearchTest : KoinTest, TestResourcesPlugin {

    val serverRule = MockWebServerRule()

    val koinRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule,
            championSearchModule
        )

        declareModule {
            single<String>(Qualifiers.serverAddress) { serverRule.url("/") }
        }
    }

    @get:Rule
    val ruleChain = RuleChain.outerRule(serverRule)
        .around(koinRule)

    @Repeat(1)
    @Test
    fun testWithKaspresso() {
        val idlingResource = OkHttp3IdlingResource.create("network-idler", get())
        IdlingRegistry.getInstance().register(idlingResource)
        with(serverRule.server) {
            enqueue(MockResponse().setAssetBody("champions_list.json"))
        }

        launchFragmentInContainer(themeResId = R.style.AppTheme) {
            ChampionSearchFragment()
        }
        val vm = getKoin().get<ChampionSearchViewModel>()
        IdlingRegistry.getInstance().register(vm.searchResource)

        onScreen<KSearchScreen> {
            queryField.typeText("nin")

            list.hasSize(2)

            list.firstChild<SearchItem> {
                hasChampionName("Ahri")
                hasChampionTitle("the Nine-Tailed Fox")
            }

            list.lastChild<SearchItem> {
                hasChampionName("Brand")
                hasChampionTitle("the Burning Vengeance")
            }
        }
    }
}