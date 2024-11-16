package luna724.iloveichika.gardening.main

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import luna724.iloveichika.gardening.Gardening.Companion.sessionPth
import java.io.File

@Serializable
data class SessionOpt(
    val coordinates: List<Double>, val orientation: List<Double>, val direction: String, val changePitch: Boolean = false
)    // coordinates: [X,Y,Z], orientation: [Yaw, Pitch], direction: anyDirection (literalString), changePitch: changePitch


fun loadSessionOpt(): LinkedHashMap<String, SessionOpt> {
    if (!File(sessionPth.toUri()).exists()) {
        File(sessionPth.toUri()).writeText("{}")
        return LinkedHashMap()
    }
    val jsonString = File(sessionPth.toUri()).readText()
    return Json.decodeFromString(jsonString)
}

fun saveSessionOpt(sessionOption: LinkedHashMap<String, SessionOpt>) {
    val jsonString = Json.encodeToString(sessionOption)
    File(sessionPth.toUri()).writeText(jsonString)
}

fun addSessionOpt(key: String, sessionOpt: SessionOpt) {
    val s = loadSessionOpt()
    s[key] = sessionOpt
    saveSessionOpt(s)
}