package luna724.iloveichika.gardening.main

import luna724.iloveichika.automove.RotationManager
import luna724.iloveichika.automove.changeDirection
import luna724.iloveichika.automove.startAutoMove
import luna724.iloveichika.automove.stopAutoMove
import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.config
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sentErrorOccurred
import kotlin.random.Random

fun swapMovement(
    yaw: Double, pitch: Double, movements: String
) {
    Thread { //TODO: ANtiAntiMacroチェックのいくつかはここに追加される
        stopAutoMove()
        if (config.antiAntiMacroMainToggle) Thread.sleep(adminConfig.antiAntiMacroMethodCooldown + Random.nextInt(0, 101))

        // Stop At Invalid Teleport を /gd による予期されるテレポートを無視するように
        if (movements == "spawn") AutoGardenOption.enableInvalidTeleportDetector = false
        changeDirection(movements)
        if (config.antiAntiMacroMainToggle) Thread.sleep(adminConfig.antiAntiMacroMethodCooldown + Random.nextInt(0, 101))

        RotationManager().startYawChanger(yaw.toFloat(), adminConfig.yawChangingTime)
        if (config.antiAntiMacroMainToggle) Thread.sleep(adminConfig.antiAntiMacroMethodCooldown + Random.nextInt(0, 101))

        startAutoMove()
    }.start()
}

private var lastTriggered: Long? = null

fun tickAutoGarden() {
    if (autoGardenIsEnable() || isSessionOptionEmpty()) return

    // XYZの取得に失敗したら、一致しないXYZを生成する
    val currentXYZ: List<Double> = getCurrentXYZ() ?: listOf(0.0, -999.0, 0.0)
    val sessionOpt: Map<String, sessionOpt>? = getSessionOption()
    sessionOpt ?: run {
        sentErrorOccurred("NullPointerException at AutoGarden:getSessionOption()")
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

    // Anti-AntiMacro StopAt n seconds Not Triggered
    val currentTime: Long = System.currentTimeMillis()
    val resizedLastTime: Long = lastTriggered ?: currentTime
    if ((currentTime - resizedLastTime) >= adminConfig.antiAntiMacroStopAtnSecondsNotTriggeredMS)
        if (adminConfig.antiAntiMacroStopAtnSecondsNotTriggered) {
            lastTriggered = null
            stopAutoGarden("§cStopped AutoGarden by Anti-AntiMacro nSecondsNotTriggered")
        }
    lastTriggered = currentTime

    // Anti-AntiMacro StopAt Invalid Teleport
    AutoGardenOption.enableInvalidTeleportDetector = true
}