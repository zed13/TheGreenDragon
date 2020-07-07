package com.valhalla.lolchampviewer.ui.champion_skills

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.agoda.kakao.common.matchers.RecyclerViewAdapterSizeMatcher
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
import com.valhalla.lolchampviewer.tools.withChildText
import com.valhalla.lolchampviewer.ui.champion_skills.pos.ChampionSkillsScreen
import com.valhalla.lolchampviewer.ui.champion_skills.pos.SkillItem
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.declareModule

class ChampionSkillsTest : KoinTest, TestResourcesPlugin, JsonTestPlugin {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule,
            championSkillsModule
        )

        declareModule {
            single<String>(Qualifiers.serverAddress) { "http://localhost" }
        }
    }

    override val json: Json
        get() = get()

    @Test
    fun testSkillFilled() {
        val champ = readEntity("champion.json", ChampionData.serializer()).champion

        launchFragmentInContainer(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundleOf("champion" to champ)
        ) {
            ChampionSkillsFragment()
        }.moveToState(Lifecycle.State.RESUMED)

        onScreen<ChampionSkillsScreen> {
            skillsList.hasSize(5)

            listOf(
                "Deathbringer Stance" to "Periodically, Aatrox's next basic attack deals bonus <physicalDamage>physical " +
                        "damage</physicalDamage> and heals him, based on the target's max health. ",
                "The Darkin Blade" to "Aatrox slams his greatsword down, dealing physical damage. He can swing three" +
                        " times, each with a different area of effect.",
                "Infernal Chains" to "Aatrox smashes the ground, dealing damage to the first enemy hit." +
                        " Champions and large monsters have to leave the impact area quickly or they" +
                        " will be dragged to the center and take the damage again.",
                "Umbral Dash" to "Passively, Aatrox heals when damaging enemy champions. On activation, he dashes in" +
                        " a direction.",
                "World Ender" to "Aatrox unleashes his demonic form, fearing nearby enemy minions and" +
                        " gaining attack damage, increased healing, and movement speed. If he gets a" +
                        " takedown, this effect is extended."
            ).forEach { (name, description) ->
                skillsList.childWith<SkillItem> {
                    withChildText(name)
                    withChildText(description)
                }.isDisplayed()
            }
        }
    }
}

