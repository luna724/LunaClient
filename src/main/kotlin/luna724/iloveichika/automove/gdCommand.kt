package luna724.iloveichika.automove

import luna724.iloveichika.lunaclient.LunaClient
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class gdCommand : CommandBase() {
    override fun getCommandName() = "garden"

    override fun getCommandAliases() = listOf("gd")

    override fun getCommandUsage(sender: ICommandSender?) = "/$commandName"

    override fun getRequiredPermissionLevel() = 0

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        LunaClient.mc.thePlayer.sendChatMessage("/warp garden")
    }
}