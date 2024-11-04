package luna724.iloveichika.gardening.main

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sentErrorOccured

fun runCmd(command: String) {
    mc.thePlayer.sendChatMessage(
        "/lc_automove:automove "+command
    )
}



fun swapMovement(
    yaw: Double, pitch: Double, movements: String
) {
    Thread {
        runCmd("")

    }.start()
}

fun tickAutoGarden() {
    if (isEnable() || isSessionOptionEmpty()) return

    // XYZの取得に失敗したら、一致しないXYZを生成する
    val currentXYZ: List<Double> = getCurrentXYZ() ?: listOf(0.0, -999.0, 0.0)
    val sessionOpt: Map<String, sessionOpt>? = getSessionOption()
    sessionOpt ?: run {
        sentErrorOccured("NullPointerException at AutoGarden:getSessionOption()")
        return
    }
    val xyzLists: List<List<Double>> = convertSessionOptToXYZLists(sessionOpt)
    val (matched, index) = checkXYZisIn(xyzLists, currentXYZ)
    if (!matched) return

    // マッチした場合
    val entriesSessionOpt = sessionOpt.entries.toList()
    val matchedSessionOpt = entriesSessionOpt[index].value

    val rotations = matchedSessionOpt.orientation
    val yaw = rotations[0]
    val pitch = rotations[1]

    val movements: String = checkDirectionsCorrectly(matchedSessionOpt.direction) ?: "reset"
    swapMovement(yaw, pitch, movements)
}