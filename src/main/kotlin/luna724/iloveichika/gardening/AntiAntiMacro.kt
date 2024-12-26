package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.gardening.Gardening.Companion.toggle
import luna724.iloveichika.gardening.main.compareXYZ
import luna724.iloveichika.gardening.main.getCurrentXYZ
import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class AntiAntiMacro {
    private var previousXYZ: List<Double> = listOf(0.0, -1.0, 0.0)
    private var previousTime: Long? = System.currentTimeMillis()
    private val delay: Long = 3000L

    private fun xyzChecker() {
        if (!LunaClient.isPlayerJoining) return
        if (!session.isEnable()) return
        val currentTime = System.currentTimeMillis()
        val effectivePreviousTime = previousTime ?: currentTime
        //println("Current Time: $currentTime, Effective Previous Time: $effectivePreviousTime, Delay: $delay")

        if ((currentTime - effectivePreviousTime) <= delay) return
        val rawXYZ = getCurrentXYZ(12) ?: previousXYZ

        //println("Current XYZ: $rawXYZ, Previous XYZ: $previousXYZ")
        if (compareXYZ(previousXYZ, rawXYZ, 1.0, 3.0)) {
            //println("XYZ Matched!")
            if (!session.isEnable()) return
            toggle.stop("§4Stopped AutoGarden by Anti-AntiMacro")
            previousXYZ = listOf(0.0,-1.0,0.0)
            return
        }
        previousXYZ = rawXYZ
        previousTime = currentTime
        //println("XYZ updated, Delay timer reset")
    }

    fun onTick(event: TickEvent.ClientTickEvent) {
        try {
            if (mc.thePlayer == null) return

            // 座標チェック
            xyzChecker()
        }
        catch (npe: NullPointerException) {
            sentErrorOccurred("NullPointerException in AntiAntiMacro.kt:onTick (currentScreen=${mc.currentScreen}")
            if (!adminConfig.antiAntiMacroKeepException) {
                toggle.stop("Auto-Garden stopped by NullPointerException in AntiAntiMacro")
            }
        }
        catch (e: Exception) {
            sentErrorOccurred("An Error Occurred in Anti-AntiMacro (AntiAntiMacro.kt:onTick)")
            if (!adminConfig.antiAntiMacroKeepException) {
                toggle.stop()
            }
        }
    }
}