package com.valhalla.lolchampviewer.ui.champion_history.pos

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.valhalla.lolchampviewer.R

class ChampionHistoryScreen : Screen<ChampionHistoryScreen>() {
    val lore: KTextView = KTextView { withId(R.id.lore) }
}