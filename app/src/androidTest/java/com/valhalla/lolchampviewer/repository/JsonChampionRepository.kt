package com.valhalla.lolchampviewer.repository

import android.content.res.Resources
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionData
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.net.models.ChampionsShortData
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class JsonChampionRepository(
    val resources: Resources,
    val championsListFileId: Int = com.valhalla.lolchampviewer.test.R.raw.champions_list,
    val championFileId: Int = com.valhalla.lolchampviewer.test.R.raw.champion
) : ChampionsRepository {

    val json = Json(JsonConfiguration(ignoreUnknownKeys = true))

    override suspend fun getChampions(): List<ChampionShort> {
        val championsListJson = String(resources.openRawResource(championsListFileId).readBytes())
        return json.parse(ChampionsShortData.serializer(), championsListJson)
            .data.values.toList()
    }

    override suspend fun getChampion(championId: String): Champion? {
        val championsListJson = String(resources.openRawResource(championFileId).readBytes())
        return json.parse(ChampionData.serializer(), championsListJson).data.values.first()
    }

}