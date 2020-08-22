package server.champions

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.nio.file.Paths

interface ChampionsRepository {
    fun getChampions(skip: Int, take: Int, locale: String = "en_US"): ChampionsListData
    fun getChampion(championId: String, locale: String = "en_US"): JsonObject?
}

class FileChampionsRepository(
    private val parser: Parser = Parser.default(),
    private val storageDir: File
) : ChampionsRepository {

    override fun getChampions(skip: Int, take: Int, locale: String): ChampionsListData {
        val championsJson =
            parser.parse(
                Paths.get(
                    storageDir.absolutePath,
                    locale,
                    "champion.json"
                ).toFile().reader()
            ) as JsonObject

        val data = championsJson.obj("data") ?: return ChampionsListData(0)

        val items = data.keys
            .asSequence()
            .sorted()
            .drop(skip)
            .take(take)
            .map { data.obj(it) }
            .filterNotNull()
            .toCollection(JsonArray())

        return ChampionsListData(data.size, items)
    }

    override fun getChampion(championId: String, locale: String): JsonObject? {
        val championJson =
            parser.parse(
                Paths.get(
                    storageDir.absolutePath,
                    locale,
                    "champion",
                    "$championId.json"
                ).toFile().reader()
            ) as JsonObject
        return championJson.obj("data")?.obj(championId)
    }
}

class ChampionsListData(
    val total: Int,
    val items: JsonArray<JsonObject> = JsonArray()
)