package luna724.iloveichika.lunaclient

import luna724.iloveichika.lunaclient.utils.DiscordWebHookUrls
import net.minecraft.util.ChatComponentText

fun sendChat(i: ChatComponentText? = null) {
    if (i == null) sentErrorOccurred("NullPointerException at sendChat")
    var msg = i
    if (LunaClient.vigilanceConfig.alwaysHeaderOnClientChats) {
            msg = ChatComponentText("${LunaClient.MAINHEADER} ").appendSibling(i) as ChatComponentText?
    }
    LunaClient.mc.thePlayer?.addChatComponentMessage(msg ?:ChatComponentText(LunaClient.ERRHEADER+"§c"+"NullPointerException at sendChat")) ?: sentErrorOccurred("NullPointerException at sendChat:thePlayer")
}

var previousErrorMessage: String? = null
fun sendChatError(txt: String) {
    if (previousErrorMessage == txt) {
        println("duplicated error in $txt")
        return
    }
    previousErrorMessage = txt
    val message = ChatComponentText(LunaClient.ERRHEADER+" §c"+txt)
    LunaClient.mc.thePlayer?.addChatComponentMessage(message) ?: println("NullPointerException Occurred at sendChatError:thePlayer")
    println("LunaClient catched ERROR: $txt")
}


@Deprecated("review all function for prevents crash (report = True = crash)")
fun sentErrorOccurred(txt: String, report: Boolean = false) {
    if (previousErrorMessage == txt) {
        println("duplicated error in $txt")
        return
    }
    previousErrorMessage = txt
    val message = ChatComponentText(LunaClient.ERRHEADER+" §c"+txt)
    LunaClient.mc.thePlayer?.addChatComponentMessage(message) ?: println("NullPointerException Occurred at sentErrorOccurred:thePlayer")

    if (report) {
        throw IllegalStateException("report are Deprecated. use sentDiscordReport instead!")
    }
}

fun sentDiscordReport(txt: String) {
    DiscordWebHookUrls.sendTextDataToDiscord(
        txt
    )
}

/**
 * need slashes! (/)
 */
fun sendCommand(s: String? = null) {
    if (s == null) sendChatError("NullPointerException at sendCommand")
    LunaClient.mc.thePlayer?.sendChatMessage(s) ?: sendChatError("NullPointerException at sendCommand:thePlayer")
}