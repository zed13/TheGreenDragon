package com.valhalla.lolchampviewer.repository

import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort

class EmptyChampionsRepository : ChampionsRepository {

    override suspend fun getChampions(): List<ChampionShort> = emptyList()

    override suspend fun getChampion(championId: String): Champion? = null

}