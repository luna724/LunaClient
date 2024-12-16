package luna724.iloveichika.gardening

import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.gardening.commands.ManageXYZ
import luna724.iloveichika.gardening.commands.ToggleAutoGardenCommand
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import java.util.*

class Command : CommandBase() {
    private val commandName: String = "lcg"
    private val commandTriggerArgs: List<String> = listOf(
        "setxyz", "removexyz", "listxyz", "currentxyz", "start", "stop", "toggle"
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
            val toggleAutoGarden = ToggleAutoGardenCommand()

            if (!commandTriggerArgs.contains(trigger)) {
                return
            }
            if (trigger.equals("start", ignoreCase = true)) {
                toggleAutoGarden.start(sender)
                return
            }
            if (trigger.equals("stop", ignoreCase = true)) {
                toggleAutoGarden.stop(sender)
                return
            }
            if (trigger.equals("toggle", ignoreCase = true)) {
                toggleAutoGarden.toggle(sender)
                return
            }

            if (trigger.equals("setxyz", ignoreCase = true)) {
                manageXYZ.setXYZ(sender, args)
                return
            }
            if (trigger.equals("removexyz", ignoreCase = true)) {
                manageXYZ.removeXYZ(sender, args)
                return
            }
            if (trigger.equals("listxyz", ignoreCase = true)) {
                manageXYZ.listXYZ(sender, args)
                return
            }
            if (trigger.equals("currentxyz", ignoreCase = true)) {
                manageXYZ.currentXYZ(sender, args)
                return
            }

        }
        catch (e: Throwable) {
            throw Exception("Error in command processing \"/lcg\"", e)
        }
    }
}