package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.main.AutoGardenOption
import luna724.iloveichika.gardening.main.getCurrentXYZ

class AntiAntiMacro {
    fun isEnable(): Boolean {
        return Gardening.config.antiAntiMacroMainToggle
    }

    fun onTick() {
        if (!isEnable()) return

        val rawXYZ = getCurrentXYZ(12)
        val xyz = getCurrentXYZ(0)
        
    }

}