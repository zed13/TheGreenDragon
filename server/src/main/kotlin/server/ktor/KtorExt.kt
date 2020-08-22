package server.ktor

import io.ktor.request.*
import server.locales.Languages

val ApplicationRequest.locale: String
    get() = header("TheDragonApi-Language")
        ?.takeIf { it in Languages.supportedLanguages }
        ?: Languages.defaultLanguage