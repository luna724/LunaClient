package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.config
import luna724.iloveichika.gardening.main.*
import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.lunaclient.sendCommand

class AntiAntiMacro {
    private var previousXYZ: List<Double> = listOf(0.0,-1.0,0.0)
    private var previousTime: Long? = null
    private val delay: Long = adminConfig.antiAntiMacroTriggerDelay

    private fun isEnable(): Boolean {
        return Gardening.config.antiAntiMacroMainToggle
    }

    fun onTick() {
        if (!isEnable()) return
        val currentTime = System.currentTimeMillis()
        val effectivePreviousTime = previousTime ?: currentTime
        if ((currentTime - effectivePreviousTime) <= delay) return

        val rawXYZ = getCurrentXYZ(12) ?: previousXYZ
        if (compareXYZ(previousXYZ, rawXYZ, 1.0, 50000.0)) {
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
    }

}