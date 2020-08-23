package com.valhalla.lolchampviewer.repository

import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort

interface ChampionsRepository {
    suspend fun getChampions(): List<ChampionShort>
    suspend fun getChampion(championId: String): Champion
}

class DragonChampionsRepository(private val api: DataDragonApi) : ChampionsRepository {

    override suspend fun getChampions(): List<ChampionShort> {
        return api.getChampions().items
            .sortedBy { it.name }
    }

    override suspend fun getChampion(championId: String): Champion {
        return api.getChampion(championId = championId)
    }
}