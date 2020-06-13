package com.valhalla.lolchampviewer.ui.champion_flow

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.valhalla.lolchampviewer.MainActivity
import com.valhalla.lolchampviewer.Qualifiers
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.mainModule
import com.valhalla.lolchampviewer.net.networkModule
import com.valhalla.lolchampviewer.repository.repositoriesModule
import com.valhalla.lolchampviewer.tools.MockWebServerRule
import com.valhalla.lolchampviewer.tools.TestResourcesPlugin
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.declareModule

class WatchSkillFlowTest : KoinTest, TestResourcesPlugin {

    val serverRule = MockWebServerRule()

    val koinRule = KoinTestRule.create {
        modules(
            mainModule,
            networkModule,
            repositoriesModule
        )

        declareModule {
            single<String>(Qualifiers.serverAddress) { serverRule.url("/") }
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

        onView(withId(R.id.skills_button)).perform(click())

        onView(withText("Orb of Deception")).check(matches(isDisplayed()))
        onView(
            withText(
                "Ahri sends out and pulls back her orb, dealing magic damage on the way out and" +
                        " true damage on the way back. After earning several spell hits, Ahri's next orb" +
                        " hits will restore her health."
            )
        ).check(matches(isDisplayed()))

    }
}