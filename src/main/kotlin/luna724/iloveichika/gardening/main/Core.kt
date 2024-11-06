package luna724.iloveichika.gardening.main

import luna724.iloveichika.gardening.Gardening
import luna724.iloveichika.lunaclient.LunaClient
import net.minecraft.util.ChatComponentText

fun stopAutoGarden(customMessage: String = "§dStopped AutoGarden by User") {
    AutoGardenOption.isEnabled = false

    LunaClient.mc.thePlayer.addChatComponentMessage(
        ChatComponentText(Gardening.HEADER + customMessage)
    )
}