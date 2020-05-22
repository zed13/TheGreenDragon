package com.valhalla.lolchampviewer.ui.champion_details.pos

import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.valhalla.lolchampviewer.R

class ChampionDetailsScreen : Screen<ChampionDetailsScreen>() {

    val champName: KTextView = KTextView { withId(R.id.name) }
    val champTitle: KTextView = KTextView { withId(R.id.title) }
    val blurb: KTextView = KTextView { withId(R.id.blurb) }
    val readLore: KButton = KButton { withId(R.id.read_lore) }
    val stats: KStatsView = KStatsView { withId(R.id.stats_container) }
    val skillsButton: KButton = KButton { withId(R.id.skills_button) }
    val skinsCarousel: KRecyclerView = KRecyclerView(
        builder = { withId(R.id.skins_carousel) },
        itemTypeBuilder = { }
    )
}