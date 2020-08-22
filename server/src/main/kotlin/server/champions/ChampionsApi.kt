package server.champions

import com.beust.klaxon.JsonObject
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Routing.championsApi(championsRepository: ChampionsRepository) {
    getChampions(championsRepository)
    getChampionById(championsRepository)
}

fun Routing.getChampions(championsRepository: ChampionsRepository) = get("/champions") {
    val championsArray = withContext(Dispatchers.IO) { championsRepository.getChampions() }
    if (championsArray == null) {
        call.respond(HttpStatusCode.InternalServerError, "Couldn't found any champions")
    } else {
        call.respond(HttpStatusCode.OK, JsonObject().apply {
            this["data"] = championsArray
            this["total"] = championsArray.size
        })
    }
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