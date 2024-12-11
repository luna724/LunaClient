package luna724.iloveichika.gardening

import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.gardening.dev.ManageXYZ
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import java.util.*

class DevCommand : CommandBase() {
    private val commandName: String = "lcg_dev"

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }

    override fun getCommandName(): String {
        return commandName
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return "/$commandName"
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        try {
            if (args.isEmpty()) {
                sendChat(
                    ChatComponentText(getCommandUsage(null))
                )
                return
            }
            val trigger: String = args[0].lowercase(Locale.getDefault())
            val manageXYZ = ManageXYZ()

            if (trigger.equals("setxyz", ignoreCase = true)) {
                manageXYZ.setXYZ(sender, args)
                return
            }
            if (trigger.equals("start", ignoreCase = true)) {
                AutoGardenCurrent.isEnabled = true
            }
            if (trigger.equals("removexyz", ignoreCase = true)) {
                //manageXYZ.removeXYZ(sender, args)
                return
            }
            if (trigger.equals("listxyz", ignoreCase = true)) {
                //manageXYZ.listXYZ(sender, args)
                return
            }
            if (trigger.equals("currentxyz", ignoreCase = true)) {
                //manageXYZ.currentXYZ(sender, args)
                return
            }
        }
        catch (e: Throwable) {
            throw Exception("Error in command processing \"/lcg\"", e)
        }
    }
}