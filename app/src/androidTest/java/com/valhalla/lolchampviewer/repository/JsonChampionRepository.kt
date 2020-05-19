package com.valhalla.lolchampviewer.repository

import android.content.res.Resources
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.net.models.ChampionsShortData
import com.valhalla.lolchampviewer.tools.TestResources
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class JsonChampionRepository(
    val resources: Resources,
    val id: Int
) : ChampionsRepository {

    val json = Json(JsonConfiguration(ignoreUnknownKeys = true))

    override suspend fun getChampions(): List<ChampionShort> {
        val championsListJson: String =
            resources.openRawResource(id).use {
                val builder = StringBuilder()
                var line: String? = null
                while (true) {
                    line = readLine()
                    if (line == null) {
                        break;
                    } else {
                        builder.append(line)
                    }
                }
                builder.toString()
            }
        return json.parse(ChampionsShortData.serializer(), championsListJson)
            .data.values.toList()
    }

    override suspend fun getChampion(championId: String): Champion? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}