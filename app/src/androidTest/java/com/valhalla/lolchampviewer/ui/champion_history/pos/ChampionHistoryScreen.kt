package com.valhalla.lolchampviewer.ui.champion_history.pos

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.kaspersky.kaspresso.screens.KScreen
import com.valhalla.lolchampviewer.R

class ChampionHistoryScreen : KScreen<ChampionHistoryScreen>() {
    val lore: KTextView = KTextView { withId(R.id.lore) }

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null
}