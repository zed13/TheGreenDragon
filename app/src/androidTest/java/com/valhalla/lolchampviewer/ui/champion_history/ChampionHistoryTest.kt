package com.valhalla.lolchampviewer.ui.champion_history

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.models.ChampionData
import com.valhalla.lolchampviewer.net.models.champion
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.JsonTestPlugin
import com.valhalla.lolchampviewer.tools.TestResourcesPlugin
import com.valhalla.lolchampviewer.ui.champion_history.pos.ChampionHistoryScreen
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.declareModule

class ChampionHistoryTest : KoinTest, TestResourcesPlugin, JsonTestPlugin {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule,
            championHistoryModule
        )

        declareModule {
            single<String>(Qualifiers.serverAddress) { "http://localhost" }
        }
    }

    override val json: Json
        get() = get()

    @Test
    fun testHistoryFilled() {
        val champ = readEntity("champion.json", ChampionData.serializer()).champion

        launchFragmentInContainer(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundleOf("champion" to champ)
        ) {
            ChampionHistoryFragment()
        }.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionHistoryScreen> {
            lore.hasText(champ.lore)
        }
    }
}