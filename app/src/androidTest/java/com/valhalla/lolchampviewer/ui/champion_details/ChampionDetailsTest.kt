package com.valhalla.lolchampviewer.ui.champion_details

import android.content.res.Resources
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import com.valhalla.lolchampviewer.repository.JsonChampionRepository
import com.valhalla.lolchampviewer.ui.Wizard
import com.valhalla.lolchampviewer.ui.champion_details.pos.ChampionDetailsScreen
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.declareModule

@RunWith(AndroidJUnit4::class)
class ChampionDetailsTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        declareModule {
            single<Resources> { InstrumentationRegistry.getInstrumentation().context.resources }
            single<Wizard> { mockk() }
            single { ChampionDetailsViewModel(get()) }
            single<ChampionsRepository> { JsonChampionRepository(get()) }
        }
    }

    @Test
    fun testDataAreSet() {
        val champ: Champion = runBlocking {
            JsonChampionRepository(get()).getChampion("")
                ?: error("champion should be stubbed here")
        }

        launchFragmentInContainer(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundleOf(Wizard.ARG_CHAMPION to champ)
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