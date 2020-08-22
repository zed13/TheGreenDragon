package server.items

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import server.klaxon.copyWith
import java.io.File
import java.nio.file.Paths

interface ItemsRepository {

    fun getItems(
        take: Int,
        skip: Int,
        locale: String = "en_US"
    ): ItemListData

    fun getItem(itemId: String, locale: String = "en_US"): JsonObject?
}

class FileItemsRepository(
    val parser: Parser = Parser.default(),
    val storageDir: File
) : ItemsRepository {

    override fun getItems(take: Int, skip: Int, locale: String): ItemListData {
        val itemsJson =
            parser.parse(
                Paths.get(
                    storageDir.absolutePath,
                    locale,
                    "item.json"
                ).toFile().reader()
            ) as JsonObject

        val data = itemsJson.obj("data") ?: return ItemListData(0)
        val items = data.keys
            .asSequence()
            .sorted()
            .drop(skip)
            .take(take)
            .map {
                data.obj(it)
                    // add id to entity, because it doesn't have id
                    ?.copyWith("id" to it)
            }
            .filterNotNull()
            .toCollection(JsonArray())

        return ItemListData(data.size, items)
    }

    override fun getItem(itemId: String, locale: String): JsonObject? {
        val itemsJson =
            parser.parse(
                Paths.get(
                    storageDir.absolutePath,
                    locale,
                    "item.json"
                ).toFile().reader()
            ) as JsonObject
        return itemsJson.obj("data")?.obj(itemId)
    }
}


class ItemListData(
    val total: Int,
    val items: JsonArray<JsonObject> = JsonArray()
)

