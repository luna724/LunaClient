package luna724.iloveichika.gardening.main

import luna724.iloveichika.gardening.AutoGardenCurrent
import luna724.iloveichika.gardening.util.SessionOpt
import luna724.iloveichika.gardening.util.loadSessionOpt

fun getSessionOption(): LinkedHashMap<String, SessionOpt>? {
    val dataMap: LinkedHashMap<String, SessionOpt> = loadSessionOpt()
    val currentSessionOption: LinkedHashMap<String, SessionOpt>? = AutoGardenCurrent.sessionOption

    if (dataMap == currentSessionOption) return currentSessionOption
    if (dataMap.isEmpty()) return null
    AutoGardenCurrent.sessionOption = dataMap

    return AutoGardenCurrent.sessionOption
}

/**
 * Checks if the AutoGarden feature is enabled.
 *
 * @return true if AutoGarden is enabled, false otherwise.
 */
fun autoGardenIsEnable(): Boolean {
    return AutoGardenCurrent.isEnabled
}

fun isSessionOptionEmpty(): Boolean {
    return getSessionOption() == null
}