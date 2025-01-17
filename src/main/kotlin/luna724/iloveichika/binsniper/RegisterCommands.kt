package luna724.iloveichika.binsniper

import luna724.iloveichika.lunaclient.sendChat
import net.minecraft.command.CommandBase
import net.minecraft.util.ChatComponentText

class RegisterCommands : CommandBase() {
    private fun help() {
        sendChat(
            ChatComponentText(
                "§e/lunaclient_binsniper §7(or §e/lcbs§7) §fto show GUI."
            )
        )
        sendChat(
            ChatComponentText(
                "§7MORE Information§f:§e preparing wiki..."
            )
        )
        return
    }

    override fun getCommandName(): String {
        return "lunaclient_binsniper"
    }

    override fun getCommandAliases(): List<String?>? {
        return listOf("lcbs", "lc_bs", "lc-bs", "lc_binsniper")
    }

    override fun getCommandUsage(sender: net.minecraft.command.ICommandSender?): String {
        return "/lunaclient_binsniper <wiki / gui>"
    }

    override fun processCommand(sender: net.minecraft.command.ICommandSender?, args: Array<String?>) {
        val trigger: String = args.getOrNull(0)?.lowercase() ?: "gui"

        when (trigger) {
            "gui" -> {
                // GUI を開くメゾット
            }
            "wiki" -> {
                sendChat(
                    ChatComponentText(
                        "§7MORE Information§f:§e https://luna724.github.io/repo/lunaclient/docs/commands.html#lc_binsniper"
                    )
                )
            }
            else -> {
                help()
            }
        }
        return
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }
}