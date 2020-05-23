package com.valhalla.lolchampviewer.ui.champion_flow

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.valhalla.lolchampviewer.MainActivity
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.MockWebServerRule
import com.valhalla.lolchampviewer.tools.TestResourcesPlugin
import com.valhalla.lolchampviewer.ui.MainWizard
import com.valhalla.lolchampviewer.ui.Wizard
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.declareModule

class WatchLoreFlowTest : KoinTest, TestResourcesPlugin {

    val serverRule = MockWebServerRule()

    val koinRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule
        )

        declareModule {
            single<String>(Qualifiers.serverAddress) { serverRule.url("/") }
            single<Wizard> { MainWizard() }
        }
    }

    @get:Rule
    val chain = RuleChain.outerRule(serverRule)
        .around(koinRule)

    @Test
    fun runFlow() {
        with(serverRule.server) {
            enqueue(MockResponse().setAssetBody("champions_list.json"))
            enqueue(MockResponse().setAssetBody("champion.json"))
        }

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        onView(withText("Ahri")).perform(click())

        onView(withId(R.id.name)).check(matches(withText("Ahri")))
        onView(withId(R.id.title)).check(matches(withText("the Nine-Tailed Fox")))

        onView(withId(R.id.read_lore)).perform(click())

        onView(withId(R.id.lore)).check(matches(withText(
            "Innately connected to the latent power of Runeterra, Ahri is a vastaya who can" +
                    " reshape magic into orbs of raw energy. She revels in toying with her prey by" +
                    " manipulating their emotions before devouring their life essence. Despite her" +
                    " predatory nature, Ahri retains a sense of empathy as she receives flashes of" +
                    " memory from each soul she consumes."
        )))
    }
}