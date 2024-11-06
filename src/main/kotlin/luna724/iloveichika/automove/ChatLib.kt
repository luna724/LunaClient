package luna724.iloveichika.automove

import luna724.iloveichika.automove.AutoMoveMod.Companion.autoMoveSettings
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiIngameMenu
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.util.ChatComponentText

private fun sendClientSideMessage(components: ChatComponentText) {
    if (!autoMoveSettings.autoMoveInfo) {
        return
    }
    val mc = Minecraft.getMinecraft()
    if (mc.currentScreen != null) {
        if (!(mc.currentScreen is GuiContainer || mc.currentScreen is GuiIngameMenu)) {
            println("prevents send data. Reason: Due to current screen")
            return
        }
    }

    if (mc.thePlayer != null) {
        mc.thePlayer.addChatMessage(components)
    }
}

private const val infoHeader = "§7[§dAutoMove§7]: §r§7"

fun sendStatusInfo(msg: String) {
    val chat = ChatComponentText(
        "$infoHeader§7[§fINFO§7]: §r§7$msg"
    )
    sendClientSideMessage(chat)
}

fun sendStatusWarn(msg: String) {
    val chat = ChatComponentText(
        "$infoHeader§7[§cWARN§7]: §r§7$msg"
    )
    sendClientSideMessage(chat)
}

fun sendStatusCritical(msg: String) {
    val chat = ChatComponentText(
        "$infoHeader§7[§4§lCRITICAL§r§7]: $msg"
    )
    sendClientSideMessage(chat)
}
