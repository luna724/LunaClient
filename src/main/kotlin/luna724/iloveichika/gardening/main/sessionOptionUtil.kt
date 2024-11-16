package luna724.iloveichika.gardening.main

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import luna724.iloveichika.gardening.Gardening.Companion.sessionPth
import java.io.File

@Serializable
data class SessionOpt(
    val coordinates: List<Double>, val orientation: List<Double>, val direction: String
)    // coordinates: [X,Y,Z], orientation: [Yaw, Pitch], direction: anyDirection (literalString)


fun loadSessionOpt(): Map<String, SessionOpt> {
    if (!File(sessionPth.toUri()).exists()) {
        File(sessionPth.toUri()).writeText("{}")
        return emptyMap()
    }
    val jsonString = File(sessionPth.toUri()).readText()
    return Json.decodeFromString(jsonString)
}

fun saveSessionOpt(sessionOption: Map<String, SessionOpt>) {
    val jsonString = Json.encodeToString(sessionOption)
    File(sessionPth.toUri()).writeText(jsonString)
}