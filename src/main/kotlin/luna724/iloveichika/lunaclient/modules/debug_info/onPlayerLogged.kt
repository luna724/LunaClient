package luna724.iloveichika.lunaclient.modules.debug_info

import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.sendChat
import net.minecraft.util.ChatComponentText

/**
 *
 */
private fun worldLoadNotifier() {
    if (!LunaClient.vigilanceConfig.WorldLoadNotifier) return

    val sendMessage = ChatComponentText(LunaClient.vigilanceConfig.worldLoadNotifierText)
    sendChat(sendMessage)
}

fun onPlayerLogged() {
   worldLoadNotifier()

}