package com.valhalla.lolchampviewer.ui.champion_skills.pos

import com.agoda.kakao.recycler.KRecyclerView
import com.kaspersky.kaspresso.screens.KScreen
import com.valhalla.lolchampviewer.R

class ChampionSkillsScreen : KScreen<ChampionSkillsScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val skillsList: KRecyclerView = KRecyclerView(
        builder = { withId(R.id.list) },
        itemTypeBuilder = {
            itemType(::SkillItem)
        }
    )
}