package com.valhalla.lolchampviewer.ui.champion_search

import com.valhalla.lolchampviewer.net.models.ChampionShort

data class ChampionSearchViewData(
    val champion: ChampionShort,
    val searchQuery: String
)