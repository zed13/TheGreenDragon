package server

import com.beust.klaxon.Parser
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import server.champions.FileChampionsRepository
import server.champions.championsApi
import server.items.FileItemsRepository
import server.items.itemsApi
import server.ktor.klaxon
import java.io.File

fun main() {
    val parser = Parser.default()
    val baseDir = File("D:/develop/ddragontail-10.9.1")
    val championsRepository = FileChampionsRepository(parser, File(baseDir, "10.9.1/data"))
    val itemsRepository = FileItemsRepository(parser, File(baseDir, "10.9.1/data"))

    embeddedServer(Netty, port = 5566) {
        install(ContentNegotiation) {
            klaxon()
        }

        routing {
            championsApi(championsRepository)
            itemsApi(itemsRepository)
        }

    }.start(wait = true)
}




