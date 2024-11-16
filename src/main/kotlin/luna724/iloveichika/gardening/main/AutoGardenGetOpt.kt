package luna724.iloveichika.gardening.main

fun getSessionOption(): LinkedHashMap<String, SessionOpt>? {
    val dataMap: LinkedHashMap<String, SessionOpt> = loadSessionOpt()
    val currentSessionOption: LinkedHashMap<String, SessionOpt>? = AutoGardenOption.sessionOption

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