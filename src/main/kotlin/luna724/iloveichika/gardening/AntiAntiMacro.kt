package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.config
import luna724.iloveichika.gardening.main.*
import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.LunaClient.Companion.scoreboardUtil
import luna724.iloveichika.lunaclient.LunaClient.Companion.tabListUtil
import luna724.iloveichika.lunaclient.sendCommand
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.regex.Pattern

class AntiAntiMacro {
    companion object {
        private var previousXYZ: List<Double> = listOf(0.0, -1.0, 0.0)
        private var previousTime: Long? = System.currentTimeMillis()
        private val delay: Long = adminConfig.antiAntiMacroTriggerDelay
    }

    private fun isEnable(): Boolean {
        return (config.antiAntiMacroMainToggle && autoGardenIsEnable())
    }

    private fun xyzChecker() {
        if (!LunaClient.isPlayerJoining) return
        if (!isEnable()) return
        val currentTime = System.currentTimeMillis()
        val effectivePreviousTime = previousTime ?: currentTime
        //println("Current Time: $currentTime, Effective Previous Time: $effectivePreviousTime, Delay: $delay")

        if ((currentTime - effectivePreviousTime) <= delay) return
        val rawXYZ = getCurrentXYZ(12) ?: previousXYZ

        //println("Current XYZ: $rawXYZ, Previous XYZ: $previousXYZ")
        if (compareXYZ(previousXYZ, rawXYZ, 1.0, 50000.0)) {
            //println("XYZ Matched!")
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
        //println("XYZ updated, Delay timer reset")
    }

    private fun anyGuestJoined(): String? {
        val tabList: List<String> = tabListUtil.getTabList()
        val result = tabListUtil.findObjRegex(tabList, "Guests (\\d+)")
        val match = result.first
        val guestString = result.second
        if (!match) { return null }

        val guestCount: Int = guestString.toIntOrNull() ?: return null
        if (guestCount >= 1) stopAutoGarden("§4Stopped AutoGarden by Anti-AntiMacro (Guest found)")
        return ""
    }

    private fun isServerClosingSoon(): String? {
        val valueList: List<String> = scoreboardUtil.getScoreboardValues()
        return null
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        try {
            if (!isEnable()) return
            xyzChecker()

            // ここから先はnullが返された場合、そこで処理を強制停止する
            anyGuestJoined() ?: return
            isServerClosingSoon() ?: return
        }
        catch (npe: NullPointerException) {
            sentErrorOccurred("NullPointerException in AntiAntiMacro.kt:onTick (currentScreen=${mc.currentScreen}")
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