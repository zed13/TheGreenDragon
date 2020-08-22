package server.klaxon

import com.beust.klaxon.JsonObject

fun JsonObject.copyWith(vararg parameter: Pair<String, Any?>): JsonObject {
    val paramsCopy = HashMap(map)
    paramsCopy.putAll(parameter)
    return JsonObject(paramsCopy)
}

fun JsonObject(vararg parameter: Pair<String, Any?>): JsonObject {
    return JsonObject(mapOf(*parameter))
}
