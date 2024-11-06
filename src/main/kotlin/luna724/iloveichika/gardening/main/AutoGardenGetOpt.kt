package luna724.iloveichika.gardening.main

import luna724.iloveichika.gardening.main.AutoGardenOption.Companion.emptySessionOption

fun getSessionOption(): Map<String, sessionOpt>? {
    val dataMap: Map<String, sessionOpt>? = loadSessionOptAsCorrectSyntax()
    val currentSessionOption: Map<String, sessionOpt>? = AutoGardenOption.sessionOption

    if (dataMap != currentSessionOption) return currentSessionOption
    AutoGardenOption.sessionOption = dataMap

    return AutoGardenOption.sessionOption
}

fun autoGardenIsEnable(): Boolean {
    return AutoGardenOption.isEnabled
}

fun isSessionOptionEmpty(): Boolean {
    if (getSessionOption() == null || getSessionOption() == emptySessionOption) return true
    return false
}