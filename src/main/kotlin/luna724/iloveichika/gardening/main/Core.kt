package luna724.iloveichika.gardening.main

import luna724.iloveichika.automove.stopAutoMove
import luna724.iloveichika.gardening.AutoGardenCurrent
import luna724.iloveichika.gardening.Gardening
import luna724.iloveichika.lunaclient.LunaClient
import net.minecraft.util.ChatComponentText

fun stopAutoGarden(customMessage: String = "§dStopped AutoGarden by User") {
    AutoGardenCurrent.isEnabled = false
    stopAutoMove()

    LunaClient.mc.thePlayer.addChatComponentMessage(
        ChatComponentText(Gardening.HEADER + customMessage)
    )
}