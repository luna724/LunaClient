package luna724.iloveichika.lunaclient

import net.minecraft.util.ChatComponentText

fun sendChat(i: ChatComponentText? = null) {
    if (i == null) sentErrorOccured("NullPointerException at sendChat")
    var msg = i
    if (LunaClient.config.alwaysHeaderOnClientChats) {
        msg = ChatComponentText(LunaClient.mainHEADER).appendSibling(i) as ChatComponentText?
    }
    LunaClient.mc.thePlayer?.addChatComponentMessage(msg ?:ChatComponentText(LunaClient.errHEADER+"§c"+"NullPointerException at sendChat")) ?: sentErrorOccured("NullPointerException at sendChat:thePlayer")
}

fun sentErrorOccured(txt: String) {
    val message = ChatComponentText(LunaClient.errHEADER+"§c"+txt)
    LunaClient.mc.thePlayer?.addChatComponentMessage(message) ?: println("NullPointerException Occurred at sentErrorOccured:thePlayer")
}

/**
 * need slashes! (/)
 */
fun sendCommand(s: String? = null) {
    if (s == null) sentErrorOccured("NullPointerException at sendCommand")
    LunaClient.mc.thePlayer?.sendChatMessage(s) ?: sentErrorOccured("NullPointerException at sendCommand:thePlayer")
}