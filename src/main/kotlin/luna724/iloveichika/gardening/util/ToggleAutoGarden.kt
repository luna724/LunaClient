package luna724.iloveichika.gardening.util

import luna724.iloveichika.automove.stopAutoMove
import luna724.iloveichika.gardening.Gardening
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.lunaclient.sendChat
import net.minecraft.util.ChatComponentText

class ToggleAutoGarden {
    fun start(customMessage: String? = "§7Started AutoGarden") {
        println("Auto-Garden is now ON!")
        session.setEnable(true)
        stopAutoMove()

        if (customMessage != null) {
            sendChat(
                ChatComponentText(
                    Gardening.HEADER + customMessage
                )
            )
        }
    }

    fun stop(customMessage: String? = "§7Stopped AutoGarden") {
        println("Auto-Garden is now OFF!")
        session.setEnable(false)
        stopAutoMove()

        if (customMessage != null) {
            sendChat(
                ChatComponentText(
                    Gardening.HEADER + customMessage
                )
            )
        }
    }
}