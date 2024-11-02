package luna724.iloveichika.lunaclient

import net.minecraft.util.ChatComponentText

fun sendChat(i: ChatComponentText? = null) {
    if (i == null) sentErrorOccured("NullPointerException at sendChat")
    var msg = i
    if (LunaClient.config.alwaysHeaderOnClientChats) {
        msg = ChatComponentText(LunaClient.mainHEADER).appendSibling(i) as ChatComponentText?
    }
    LunaClient.mc.thePlayer.addChatComponentMessage(msg ?:ChatComponentText(LunaClient.errHEADER+"§c"+"NullPointerException at sendChat"))
}

fun sentErrorOccured(txt: String) {
    val message = ChatComponentText(LunaClient.errHEADER+"§c"+txt)
    LunaClient.mc.thePlayer.addChatComponentMessage(message)
}