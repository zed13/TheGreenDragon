package com.valhalla.lolchampviewer.ui.champions_list.pos

import android.view.View
import android.widget.TextView
import androidx.test.espresso.ViewAssertion
import com.agoda.kakao.common.assertions.BaseAssertions
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import com.valhalla.lolchampviewer.R
import org.hamcrest.Matcher

class ChampionItem(parent: Matcher<View>) : KRecyclerItem<ChampionItem>(parent), BaseAssertions {

    val champNameView = KTextView(parent) { withId(R.id.champ_name) }
    val champTitleView = KTextView(parent) { withId(R.id.champ_title) }

    fun hasChampionName(name: CharSequence) {
        view.check(ViewAssertion { view, noViewFoundException ->
            if (view is TextView) {
                if (view.text != name) {
                    throw AssertionError(
                        "Expected champion name is $name" +
                                " but found ${view.text}"
                    )
                }
            } else {
                noViewFoundException?.let { AssertionError(it) }
            }
        })
    }

    fun hasChampionTitle(title: CharSequence) {
        view.check(ViewAssertion { view, noViewFoundException ->
            if (view is TextView) {
                if (view.text != title) {
                    throw AssertionError(
                        "Expected champion title is $title" +
                                " but found ${view.text}"
                    )
                }
            } else {
                noViewFoundException?.let { AssertionError(it) }
            }
        })
    }
}
