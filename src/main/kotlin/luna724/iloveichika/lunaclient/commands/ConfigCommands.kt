package luna724.iloveichika.lunaclient.commands

import luna724.iloveichika.lunaclient.config.Config
import luna724.iloveichika.lunaclient.LunaClient
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class ExampleCommand : CommandBase() {
    override fun getCommandName() = "lunaclient"

    override fun getCommandAliases() = listOf("lc", "luna")

    override fun getCommandUsage(sender: ICommandSender?) = "/$commandName"

    override fun getRequiredPermissionLevel() = 0

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        LunaClient.currentGui = Config.gui()
    }
}