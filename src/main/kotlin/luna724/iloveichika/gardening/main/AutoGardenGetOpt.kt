package luna724.iloveichika.gardening.main

import luna724.iloveichika.gardening.AutoGardenSession
import luna724.iloveichika.gardening.util.SessionOpt
import luna724.iloveichika.gardening.util.loadSessionOpt

fun getSessionOption(): LinkedHashMap<String, SessionOpt>? {
    val dataMap: LinkedHashMap<String, SessionOpt> = loadSessionOpt()
    val currentSessionOption: LinkedHashMap<String, SessionOpt>? = AutoGardenSession.sessionOption

    if (dataMap == currentSessionOption) return currentSessionOption
    if (dataMap.isEmpty()) return null
    AutoGardenSession.sessionOption = dataMap

    return AutoGardenSession.sessionOption
}