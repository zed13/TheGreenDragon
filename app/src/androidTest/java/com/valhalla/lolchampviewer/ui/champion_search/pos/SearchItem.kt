package com.valhalla.lolchampviewer.ui.champion_search.pos

import android.view.View
import com.agoda.kakao.common.assertions.BaseAssertions
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import com.valhalla.lolchampviewer.R
import org.hamcrest.Matcher

class SearchItem(parent: Matcher<View>) : KRecyclerItem<SearchItem>(parent), BaseAssertions {

    val champNameView = KTextView(parent) { withId(R.id.champ_name) }
    val champTitleView = KTextView(parent) { withId(R.id.champ_title) }

    fun hasChampionName(name: String) {
        champNameView.hasText(name)
    }

    fun hasChampionTitle(title: String) {
        champTitleView.hasText(title)
    }
}