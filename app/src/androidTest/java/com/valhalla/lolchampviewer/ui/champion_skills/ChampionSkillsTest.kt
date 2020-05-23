package com.valhalla.lolchampviewer.ui.champion_skills

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.agoda.kakao.common.matchers.RecyclerViewAdapterSizeMatcher
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.models.ChampionData
import com.valhalla.lolchampviewer.net.models.champion
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.JsonTestPlugin
import com.valhalla.lolchampviewer.tools.TestResourcesPlugin
import com.valhalla.lolchampviewer.ui.Wizard
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
            fragmentArgs = bundleOf(Wizard.ARG_CHAMPION to champ)
        ) {
            ChampionSkillsFragment()
        }.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.list))
            .check(matches(RecyclerViewAdapterSizeMatcher(5)))

        // check passive content set
        onView(withText("Deathbringer Stance")).check(matches(isDisplayed()))
        onView(
            withText(
                "Periodically, Aatrox's next basic attack deals bonus <physicalDamage>physical " +
                        "damage</physicalDamage> and heals him, based on the target's max health. "
            )
        ).check(matches(isDisplayed()))

        // check Q content set
        onView(withText("The Darkin Blade")).check(matches(isDisplayed()))
        onView(
            withText(
                "Aatrox slams his greatsword down, dealing physical damage. He can swing three" +
                        " times, each with a different area of effect."
            )
        ).check(matches(isDisplayed()))

        // check W content set
        onView(withText("Infernal Chains")).check(matches(isDisplayed()))
        onView(
            withText(
                "Aatrox smashes the ground, dealing damage to the first enemy hit." +
                        " Champions and large monsters have to leave the impact area quickly or they" +
                        " will be dragged to the center and take the damage again."
            )
        ).check(matches(isDisplayed()))

        // check E content set
        onView(withText("Umbral Dash")).check(matches(isDisplayed()))
        onView(
            withText(
                "Passively, Aatrox heals when damaging enemy champions. On activation, he dashes in" +
                        " a direction."
            )
        ).check(matches(isDisplayed()))

        // check R content set
        onView(withText("World Ender")).check(matches(isDisplayed()))
        onView(
            withText(
                "Aatrox unleashes his demonic form, fearing nearby enemy minions and" +
                        " gaining attack damage, increased healing, and movement speed. If he gets a" +
                        " takedown, this effect is extended."
            )
        ).check(matches(isDisplayed()))

        // todo: Figure out how to make kakao works here
//        onScreen<ChampionSkillsScreen> {
//            skillsList.hasSize(5)
//            skillsList.firstChild<SkillItem> {
//                spellName.hasText("Deathbringer Stance")
//                spellDescription.hasText("Periodically, Aatrox's next basic attack deals bonus <physicalDamage>physical damage</physicalDamage> and heals him, based on the target's max health. ")
//            }
//            skillsList.childAt<SkillItem>(1) {
//                spellName.hasText("The Darkin Blade")
//                spellDescription.hasText("Aatrox slams his greatsword down, dealing physical damage. He can swing three times, each with a different area of effect.")
//            }
//            skillsList.childAt<SkillItem>(2) {
//                spellName.hasText("Infernal Chains")
//                spellDescription.hasText("Aatrox smashes the ground, dealing damage to the first enemy hit. Champions and large monsters have to leave the impact area quickly or they will be dragged to the center and take the damage again.")
//            }
//            skillsList.childAt<SkillItem>(3) {
//                spellName.hasText("Umbral Dash")
//                spellDescription.hasText("Passively, Aatrox heals when damaging enemy champions. On activation, he dashes in a direction.")
//            }
//            skillsList.childAt<SkillItem>(4) {
//                spellName.hasText("World Ender")
//                spellDescription.hasText("Aatrox unleashes his demonic form, fearing nearby enemy minions and gaining attack damage, increased healing, and movement speed. If he gets a takedown, this effect is extended.")
//            }
//    }
    }
}