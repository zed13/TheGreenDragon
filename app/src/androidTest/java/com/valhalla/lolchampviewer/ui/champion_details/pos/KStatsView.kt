package com.valhalla.lolchampviewer.ui.champion_details.pos

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewAssertion
import com.agoda.kakao.common.assertions.BaseAssertions
import com.agoda.kakao.common.builders.ViewBuilder
import com.agoda.kakao.common.views.KBaseView
import org.hamcrest.Matcher

class KStatsView : KBaseView<KStatsView>, BaseAssertions {
    constructor(function: ViewBuilder.() -> Unit) : super(function)
    constructor(parent: Matcher<View>, function: ViewBuilder.() -> Unit) : super(parent, function)
    constructor(parent: DataInteraction, function: ViewBuilder.() -> Unit) : super(parent, function)

    fun hasStats(stats: List<String>) {
        view.check(ViewAssertion { view, noViewFoundException ->
            if (view is ViewGroup) {
                val statLines = view.children
                    .map { it as TextView }
                    .map { it.text.toString() }
                    .toList()

                if (stats.size != statLines.size) {
                    throw AssertionError(
                        "Expected stats count is ${stats.size}" +
                                " but found ${statLines.size}"
                    )
                }
                for (i in stats.indices) {
                    if (stats[i] != statLines[i]) {
                        throw AssertionError(
                            "Expected stats is $stats" +
                                    " but found $statLines"
                        )
                    }
                }
            } else {
                noViewFoundException?.let { AssertionError(it) }
            }
        })
    }

}