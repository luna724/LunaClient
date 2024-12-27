package luna724.iloveichika.gardening.commands;

import luna724.iloveichika.gardening.Gardening
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.gardening.Gardening.Companion.session
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class ToggleAutoGardenCommand {
    private val HEADER: String = "§6[§2Auto-Garden§6]§f: "
    /**コマンド実行失敗*/
    private fun sendMsg(e: String) {
        mc.thePlayer.addChatComponentMessage(
            ChatComponentText(
                HEADER+e
            )
        )
    }

    fun start(
        sender: ICommandSender
    ) {
        if (session.isEnable()) {
            sendMsg("§cAuto-Garden is already enabled!")
            return
        }
        session.start("§6Auto-Garden Started.")
    }

    fun stop(
        sender: ICommandSender
    ) {
        if (!session.isEnable()) {
            sendMsg("§cAuto-Garden is already disabled!")
            return
        }
        session.stop("§6Auto-Garden Stopped.")
    }

    fun toggle(
        sender: ICommandSender
    ) {
        if (session.isEnable()) {
            stop(sender)
        }
        else {
            start(sender)
        }
    }
}
