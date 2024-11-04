package luna724.iloveichika.lunaclient.commands

import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.config.Config
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class SimplyLimbo : CommandBase() {
    override fun getCommandName() = "simply_limboed"

    override fun getCommandAliases() = listOf("spam2limbo")

    override fun getCommandUsage(sender: ICommandSender?) = "/$commandName"

    override fun getRequiredPermissionLevel() = 0

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        for (x in listOf(1,2,3,4,5,6,7,8,9,10,11,12,13)) {
            LunaClient.mc.thePlayer.sendChatMessage("/this_empty_commands_for_limbo!")
        }
    }
}