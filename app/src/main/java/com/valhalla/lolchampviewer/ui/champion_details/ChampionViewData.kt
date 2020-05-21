package com.valhalla.lolchampviewer.ui.champion_details

import com.valhalla.lolchampviewer.net.models.Champion

data class ChampionViewData(
    val name: String,
    val title: String,
    val blurb: String
) {
    constructor(champion: Champion) : this(
        name = champion.name,
        title = champion.title,
        blurb = champion.blurb
    )
}