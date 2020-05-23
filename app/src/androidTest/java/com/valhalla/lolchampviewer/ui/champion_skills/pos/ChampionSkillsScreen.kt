package com.valhalla.lolchampviewer.ui.champion_skills.pos

import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.valhalla.lolchampviewer.R

class ChampionSkillsScreen : Screen<ChampionSkillsScreen>() {
    val skillsList: KRecyclerView = KRecyclerView(
        builder = { withId(R.id.list) },
        itemTypeBuilder = { itemType(::SkillItem) }
    )
}