package com.valhalla.lolchampviewer.ui.champions_list.pos

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.valhalla.lolchampviewer.R

class ChampionsListScreen : Screen<ChampionsListScreen>() {
    val list = KRecyclerView(
        builder = { withId(R.id.list) },
        itemTypeBuilder = { itemType(::ChampionItem) }
    )
    val progressView = KView { withId(R.id.progress) }
    val errorView = KView { withId(R.id.error) }
    val errorLabelView = KTextView { withId(R.id.error_label) }
    val actionProgressView = KTextView { withId(R.id.action_progress) }

    fun isListShown() {
        list.isVisible()
        progressView.isGone()
        errorView.isGone()
        actionProgressView.isGone()
    }

    fun isErrorShown(text: String) {
        errorView.isVisible()
        list.isGone()
        progressView.isGone()
        actionProgressView.isGone()
        errorLabelView.hasText(text)
    }

    fun isProgressShown() {
        list.isGone()
        progressView.isVisible()
        errorView.isGone()
        actionProgressView.isGone()
    }

    fun isActionProgressShown() {
        list.isGone()
        progressView.isGone()
        errorView.isGone()
        actionProgressView.isVisible()
    }
}