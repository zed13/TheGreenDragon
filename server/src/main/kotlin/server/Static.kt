package server

import io.ktor.http.content.*
import io.ktor.routing.*
import java.io.File

fun Routing.staticData(baseDir: File) = static("img") {
    static("champion") {
        files(File(baseDir, "10.9.1/img/champion"))
        static("skin") {
            files(File(baseDir, "img/champion/splash"))
        }
    }
    static("skill") {
        static("passive") {
            files(File(baseDir, "10.9.1/img/passive"))
        }
        static("spell") {
            files(File(baseDir, "10.9.1/img/spell"))
        }
    }

}