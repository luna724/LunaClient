package luna724.iloveichika.lunaclient.commands

import luna724.iloveichika.lunaclient.LunaClient
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraftforge.client.ClientCommandHandler
import luna724.iloveichika.lunaclient.commands.SimpleCommand.ProcessCommandRunnable
import luna724.iloveichika.lunaclient.sentDiscordReport
import luna724.iloveichika.lunaclient.utils.openFolder


class CommandManager {
    init {
        registerCommand("lc") {
            LunaClient.configManager.openConfigGui()
        }

        registerCommand("simply_limboed") {
            for (x in listOf(1,2,3,4,5,6,7,8,9,10,11,12,13)) {
                LunaClient.mc.thePlayer.sendChatMessage("/this_empty_commands_for_limbo!")
            }
        }

        registerCommand("simulate_error") {
            sentDiscordReport("Simulate error from Minecraft!")
        }

        registerCommand("lc-cfg") {
            openFolder(LunaClient.configDirectory.toPath().toAbsolutePath().toString())
        }
    }

    private fun registerCommand(name: String, function: (Array<String>) -> Unit) {
        ClientCommandHandler.instance.registerCommand(SimpleCommand(name, createCommand(function)))
    }

    private fun createCommand(function: (Array<String>) -> Unit) = object : ProcessCommandRunnable() {
        override fun processCommand(sender: ICommandSender?, args: Array<String>?) {
            if (args != null) function(args.asList().toTypedArray())
        }
    }
}