package server.champions

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

interface ChampionsRepository {
    fun getChampions(locale: String = "en_US"): JsonArray<*>?
    fun getChampion(championId: String, locale: String = "en_US"): JsonObject?
}

class FileChampionsRepository(
    val parser: Parser = Parser.default(),
    val storageDir: File
) : ChampionsRepository {

    override fun getChampions(locale: String): JsonArray<*>? {
        val championsJson =
            parser.parse(
                Paths.get(
                    storageDir.absolutePath,
                    locale,
                    "champion.json"
                ).toFile().reader()
            ) as JsonObject

        val data = championsJson.obj("data")
        val array = JsonArray<JsonObject>()
        data?.values
            ?.map { it as JsonObject }
            ?.forEach { array.add(it) }
        return array
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

fun Paths.get(first: File, vararg more: String): Path {
    return Paths.get(first.absolutePath, *more)
}