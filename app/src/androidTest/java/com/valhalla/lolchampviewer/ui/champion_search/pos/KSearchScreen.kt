package com.valhalla.lolchampviewer.ui.champion_search.pos

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerView
import com.kaspersky.kaspresso.screens.KScreen
import com.valhalla.lolchampviewer.R

class KSearchScreen : KScreen<KSearchScreen>() {
    override val layoutId: Int? = R.layout.fragment_champion_search
    override val viewClass: Class<*>? = null

    val list = KRecyclerView(
        builder = { withId(R.id.list) },
        itemTypeBuilder = { itemType(::SearchItem) }
    )

    val queryField = KEditText { withId(R.id.query_field) }
}