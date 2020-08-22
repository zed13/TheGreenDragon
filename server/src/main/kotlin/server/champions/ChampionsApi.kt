package server.champions

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import server.guard
import server.klaxon.JsonObject

fun Routing.championsApi(championsRepository: ChampionsRepository) {
    getChampions(championsRepository)
    getChampionById(championsRepository)
}

fun Routing.getChampions(championsRepository: ChampionsRepository) = get("/champions") {
    val take: Int = call.request.queryParameters["take"]?.toIntOrNull()
        .guard {
            call.respond(
                HttpStatusCode.BadRequest,
                "There is no 'take' parameter"
            ); return@get
        }

    val skip = call.request.queryParameters["skip"]?.toIntOrNull() ?: 0

    val championsData = withContext(Dispatchers.IO) { championsRepository.getChampions(skip, take) }
    call.respond(
        status = HttpStatusCode.OK,
        message = JsonObject(
            "total" to championsData.total,
            "items" to championsData.items
        )
    )
}

fun Routing.getChampionById(championsRepository: ChampionsRepository) = get("/champion/{championId}") {
    val championId = call.parameters["championId"]
    if (championId == null) {
        call.respond(HttpStatusCode.BadRequest, "chamionId param is missed")
        return@get
    }
    val champion = withContext(Dispatchers.IO) { championsRepository.getChampion(championId) }
    if (champion == null) {
        call.respond(HttpStatusCode.NotFound, "Champion with id $championId is not found")
        return@get
    }
    call.respond(HttpStatusCode.OK, champion)
}