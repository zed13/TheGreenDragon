package server.items

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import server.guard
import server.klaxon.JsonObject

fun Routing.itemsApi(itemsRepository: ItemsRepository) {
    getItem(itemsRepository)
    getItems(itemsRepository)
}

fun Routing.getItems(itemsRepository: ItemsRepository) = get("/items") {
    val take: Int = call.request.queryParameters["take"]?.toIntOrNull()
        .guard {
            call.respond(
                HttpStatusCode.BadRequest,
                "There is no take parameter"
            ); return@get
        }

    val skip = call.request.queryParameters["skip"]?.toIntOrNull() ?: 0

    val items = withContext(Dispatchers.IO) {
        itemsRepository.getItems(take, skip).let {
            JsonObject(
                "total" to it.total,
                "items" to it.items
            )
        }
    }

    call.respond(HttpStatusCode.OK, items)
}

fun Routing.getItem(itemsRepository: ItemsRepository) = get("/item/{itemId}") {
    val itemId: String = call.parameters["itemId"].guard {
        call.respond(
            HttpStatusCode.BadRequest,
            "There is no itemId parameter"
        ); return@get
    }

    val item = withContext(Dispatchers.IO) {
        itemsRepository.getItem(itemId)
    }

    if (item == null) {
        call.respond(HttpStatusCode.NotFound, "Item with id $itemId is not found")
    } else {
        call.respond(HttpStatusCode.OK, item)
    }
}