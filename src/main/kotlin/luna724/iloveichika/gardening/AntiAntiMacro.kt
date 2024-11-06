package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.config
import luna724.iloveichika.gardening.main.*
import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.sendCommand
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class AntiAntiMacro {
    companion object {
        private var previousXYZ: List<Double> = listOf(0.0, -1.0, 0.0)
        private var previousTime: Long? = System.currentTimeMillis()
        private val delay: Long = adminConfig.antiAntiMacroTriggerDelay
    }

    private fun isEnable(): Boolean {
        return (Gardening.config.antiAntiMacroMainToggle && autoGardenIsEnable())
    }

    private fun xyzChecker() {
        if (!LunaClient.isPlayerJoining) return
        if (!isEnable()) return
        val currentTime = System.currentTimeMillis()
        val effectivePreviousTime = previousTime ?: currentTime
        println("Current Time: $currentTime, Effective Previous Time: $effectivePreviousTime, Delay: $delay")

        if ((currentTime - effectivePreviousTime) <= delay) return
        val rawXYZ = getCurrentXYZ(12) ?: previousXYZ

        println("Current XYZ: $rawXYZ, Previous XYZ: $previousXYZ")
        if (compareXYZ(previousXYZ, rawXYZ, 1.0, 50000.0)) {
            println("XYZ Matched!")
            if (!isEnable()) return
            if (!autoGardenIsEnable()) return
            stopAutoGarden("§4Stopped AutoGarden by Anti-AntiMacro")
            if (config.antiAntiMacroCommand) {
                sendCommand("/" + config.antiAntiMacroCommands)
            }
            previousXYZ = listOf(0.0,-1.0,0.0)
            return
        }
        previousXYZ = rawXYZ
        previousTime = currentTime
        println("XYZ updated, Delay timer reset")
    }

    private fun anyGuestJoined(): String? {
        return null
    }


    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        try {
            if (!isEnable()) return
            xyzChecker()

            // ここから先はnullが返された場合、そこで処理を強制停止する
            anyGuestJoined() ?: return
        }
        catch (npe: NullPointerException) {
            sentErrorOccurred("NullPointerException in AntiAntiMacro.kt:onTick")
            if (!adminConfig.antiAntiMacroKeepException) {
                stopAutoGarden()
            }
        }
        catch (e: Exception) {
            sentErrorOccurred("An Error Occurred in Anti-AntiMacro (AntiAntiMacro.kt:onTick)")
            if (!adminConfig.antiAntiMacroKeepException) {
                stopAutoGarden()
            }
        }
    }
}