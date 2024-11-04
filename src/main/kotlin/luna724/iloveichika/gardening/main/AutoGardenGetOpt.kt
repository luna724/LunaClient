package luna724.iloveichika.gardening.main

import com.google.gson.reflect.TypeToken
import luna724.iloveichika.gardening.Gardening
import luna724.iloveichika.gardening.Gardening.Companion.sessionPth
import luna724.iloveichika.gardening.main.AutoGardenOption.Companion.emptySessionOption
import luna724.iloveichika.gardening.main.AutoGardenOption.Companion.gson
import luna724.iloveichika.gardening.main.AutoGardenOption.Companion.mapType
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

fun getSessionOption(): Map<String, sessionOpt>? {
    val dataMap: Map<String, sessionOpt>? = loadSessionOptAsCorrectSyntax()
    val currentSessionOption: Map<String, sessionOpt>? = AutoGardenOption.sessionOption

    if (dataMap != currentSessionOption) return currentSessionOption
    AutoGardenOption.sessionOption = dataMap

    return AutoGardenOption.sessionOption
}

fun isEnable(): Boolean {
    return AutoGardenOption.isEnabled
}

fun isSessionOptionEmpty(): Boolean {
    if (getSessionOption() == null || getSessionOption() == emptySessionOption) return true
    return false
}