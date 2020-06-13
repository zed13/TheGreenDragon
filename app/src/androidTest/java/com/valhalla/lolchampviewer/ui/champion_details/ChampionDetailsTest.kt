package com.valhalla.lolchampviewer.ui.champion_details

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
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
import com.valhalla.lolchampviewer.ui.champion_details.pos.ChampionDetailsScreen
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.declareModule

@RunWith(AndroidJUnit4::class)
class ChampionDetailsTest : KoinTest, TestResourcesPlugin, JsonTestPlugin {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule,
            championDetailsModule
        )

        declareModule {
            single<String>(Qualifiers.serverAddress) { "http://localhost" }
        }
    }

    override val json: Json
        get() = get()

    @kotlinx.serialization.ImplicitReflectionSerializer
    @Test
    fun testDataAreSet() {
        val champ = readEntity("champion.json", ChampionData.serializer()).champion

        launchFragmentInContainer(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundleOf("champion" to champ)
        ) {
            ChampionDetailsFragment()
        }.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionDetailsScreen> {
            champName.hasText("Aatrox")
            champTitle.hasText("the Darkin Blade")
            blurb.hasText(
                "Once honored defenders of Shurima against the Void, Aatrox and " +
                        "his brethren would eventually become an even greater threat to Runeterra, and " +
                        "were defeated only by cunning mortal sorcery. But after centuries of " +
                        "imprisonment, Aatrox was the first to find..."
            )
            readLore.isVisible()
            skillsButton.isVisible()
            skinsCarousel.hasSize(7)
            stats.hasStats(champ.stats.toList().map { it.format() })
        }
    }
}