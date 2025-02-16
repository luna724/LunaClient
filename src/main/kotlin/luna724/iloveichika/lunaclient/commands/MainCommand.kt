package luna724.iloveichika.lunaclient.commands

import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.sendChatError
import luna724.iloveichika.lunaclient.sentErrorOccurred
import luna724.iloveichika.lunaclient.utils.openFolder
import net.minecraft.command.CommandBase

class MainCommand : CommandBase() {
    override fun getCommandName() = "lunaclient"
    override fun getCommandAliases() = listOf("lc")
    override fun getCommandUsage(sender: net.minecraft.command.ICommandSender?) = "/$commandName"

    override fun getRequiredPermissionLevel() = 0
    override fun processCommand(sender: net.minecraft.command.ICommandSender?, args: Array<out String>?) {
        val trigger: String = args?.getOrNull(0) ?: ""

        when (trigger) {
            "" -> LunaClient.configManager.openConfigGui()
            "gui" -> LunaClient.configManager.openConfigGui()
            "files" -> openFolder(LunaClient.configDirectory.toPath().toAbsolutePath().toString())

            else -> sendChatError("Unknown subcommand: $trigger")
        }
    }
}