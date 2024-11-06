package luna724.iloveichika.gardening.main

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import luna724.iloveichika.gardening.Gardening.Companion.sessionPth
import java.io.File

data class sessionOpt(
    val coordinates: List<Double>, val orientation: List<Double>, val direction: String
)    // coordinates: [X,Y,Z], orientation: [Yaw, Pitch], direction: anyDirection (literalString)


fun loadSessionOptAsCorrectSyntax(): Map<String, sessionOpt>? {
    val gson = Gson()
    val jsonString = File(sessionPth.toUri()).readText()
    val mapType = object : TypeToken<Map<String, sessionOpt>>() {}.type
    return gson.fromJson(jsonString, mapType)
}

fun saveSessionOpt(sessionOption: Map<String, sessionOpt>) {
    val gson = Gson()
    val jsonString = gson.toJson(sessionOption)
    File(sessionPth.toUri()).writeText(jsonString)
}