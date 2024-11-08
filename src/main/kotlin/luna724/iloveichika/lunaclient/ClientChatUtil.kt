package luna724.iloveichika.lunaclient

import luna724.iloveichika.discordWebHookUrls.errorReportingServer
import luna724.iloveichika.discordWebHookUrls.sendTextDataToDiscord
import net.minecraft.util.ChatComponentText

fun sendChat(i: ChatComponentText? = null) {
    if (i == null) sentErrorOccurred("NullPointerException at sendChat")
    var msg = i
    if (LunaClient.vigilanceConfig.alwaysHeaderOnClientChats) {
        msg = ChatComponentText(LunaClient.mainHEADER).appendSibling(i) as ChatComponentText?
    }
    LunaClient.mc.thePlayer?.addChatComponentMessage(msg ?:ChatComponentText(LunaClient.errHEADER+"§c"+"NullPointerException at sendChat")) ?: sentErrorOccurred("NullPointerException at sendChat:thePlayer")
}

fun sentErrorOccurred(txt: String, report: Boolean = true) {
    val message = ChatComponentText(LunaClient.errHEADER+"§c"+txt)
    LunaClient.mc.thePlayer?.addChatComponentMessage(message) ?: println("NullPointerException Occurred at sentErrorOccurred:thePlayer")

    if (!report) return
    try {
        sendTextDataToDiscord(txt, errorReportingServer)
        return
    } catch (e: Exception) {
        return
    }
}

/**
 * need slashes! (/)
 */
fun sendCommand(s: String? = null) {
    if (s == null) sentErrorOccurred("NullPointerException at sendCommand")
    LunaClient.mc.thePlayer?.sendChatMessage(s) ?: sentErrorOccurred("NullPointerException at sendCommand:thePlayer")
}