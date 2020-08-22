package server.locales

import com.beust.klaxon.JsonArray
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import server.klaxon.JsonObject

fun Routing.supportedLanguages() = get("/languages") {
    call.respond(
        HttpStatusCode.OK, JsonObject(
            "defaultLanguage" to Languages.defaultLanguage,
            "languages" to JsonArray(*Languages.supportedLanguages)
        )
    )
}