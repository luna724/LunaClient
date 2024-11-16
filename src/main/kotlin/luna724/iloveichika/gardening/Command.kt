package luna724.iloveichika.gardening

import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.gardening.main.ManageXYZ
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import java.util.*

class Command : CommandBase() {
    private val commandName: String = "lcg"
    private val commandTriggerArgs: List<String> = listOf(
        "setxyz", "removexyz", "listxyz", "currentxyz"
    )

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }

    override fun getCommandName(): String {
        return commandName
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return "/$commandName $commandTriggerArgs"
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

            if (!commandTriggerArgs.contains(trigger)) {
                sentErrorOccurred("Unknown trigger: $trigger")
                return
            }

            if (trigger.equals("setxyz", ignoreCase = true)) {
                manageXYZ.setXYZ(sender, args)
                return
            }
            if (trigger.equals("removexyz", ignoreCase = true)) {
                manageXYZ.removeXYZ(sender, args)
            }
            if (trigger.equals("listxyz", ignoreCase = true)) {
                manageXYZ.listXYZ(sender, args)
            }
            if (trigger.equals("currentxyz", ignoreCase = true)) {
                manageXYZ.currentXYZ(sender, args)
            }
        }
        catch (e: Throwable) {
            throw Exception("Error in command processing \"/lcg\"", e)
        }
    }
}