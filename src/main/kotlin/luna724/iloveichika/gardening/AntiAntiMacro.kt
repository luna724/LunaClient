package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.playerPosUtil
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Deprecated("REVAMP THIS!!!")
class AntiAntiMacro {
    private var previousXYZ: List<Double> = listOf(0.0, -1.0, 0.0)
    private var previousTime: Long? = System.currentTimeMillis()
    private val delay: Long = 3000L

    @Deprecated("REVAMP THIS!!!")
    private fun xyzChecker() {
        if (!LunaClient.isPlayerJoining) return
        if (!session.isEnable()) return
        val currentTime = System.currentTimeMillis()
        val effectivePreviousTime = previousTime ?: currentTime
        //println("Current Time: $currentTime, Effective Previous Time: $effectivePreviousTime, Delay: $delay")

        if ((currentTime - effectivePreviousTime) <= delay) return
        val rawXYZ = playerPosUtil.getPlayerPosition(12)

        //println("Current XYZ: $rawXYZ, Previous XYZ: $previousXYZ")
        if (playerPosUtil.compareXYZ(previousXYZ, rawXYZ, 1.0, 3.0)) {
            //println("XYZ Matched!")
            if (!session.isEnable()) return
            session.stop("§4Stopped AutoGarden by Anti-AntiMacro")
            previousXYZ = listOf(0.0,-1.0,0.0)
            return
        }
        previousXYZ = rawXYZ
        previousTime = currentTime
        //println("XYZ updated, Delay timer reset")
    }

    @SubscribeEvent
    @Deprecated("REVAMP THIS!!!")
    fun onTick(event: TickEvent.ClientTickEvent) {
        // 一時的に無効化
        return

        try {
            if (mc.thePlayer == null) return

            xyzChecker()
        }
        catch (npe: NullPointerException) {
            sentErrorOccurred("NullPointerException in AntiAntiMacro.kt:onTick (currentScreen=${mc.currentScreen}")
            if (!adminConfig.antiAntiMacroKeepException) {
                session.stop("Auto-Garden stopped by NullPointerException in AntiAntiMacro")
            }
        }
        catch (e: Exception) {
            sentErrorOccurred("An Error Occurred in Anti-AntiMacro (AntiAntiMacro.kt:onTick)")
            if (!adminConfig.antiAntiMacroKeepException) {
                session.stop()
            }
        }
    }
}