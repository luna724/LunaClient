package luna724.iloveichika.gardening.main

fun getSessionOption(): Map<String, SessionOpt>? {
    val dataMap: Map<String, SessionOpt> = loadSessionOpt()
    val currentSessionOption: Map<String, SessionOpt>? = AutoGardenOption.sessionOption

    if (dataMap == currentSessionOption) return currentSessionOption
    if (dataMap.isEmpty()) return null
    AutoGardenOption.sessionOption = dataMap

    return AutoGardenOption.sessionOption
}

fun autoGardenIsEnable(): Boolean {
    return AutoGardenOption.isEnabled
}

fun isSessionOptionEmpty(): Boolean {
    if (getSessionOption() == null) return true
    return false
}