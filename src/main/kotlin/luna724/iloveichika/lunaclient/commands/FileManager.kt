package luna724.iloveichika.lunaclient.commands

import luna724.iloveichika.lunaclient.LunaClient.Companion.configDirectory
import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.lunaclient.sendCommand
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import java.io.File

class FileManager : CommandBase() {
    private val acceptCommand: List<String> = listOf("run")
    private val commandUsage = "/lc_filemanager help: https://luna724.github.io/repo/lunaclient/docs/commands.html#lc-filemanger"

    override fun getCommandName() = "lc_filemanager"
    override fun getCommandAliases() = listOf("lc_file")
    override fun getCommandUsage(sender: ICommandSender) = commandUsage
    override fun getRequiredPermissionLevel() = 0

    private fun sendError(error: String) {
        sentErrorOccurred("§r §7[§6LC-FileManager§7] §r$error")
    }

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        if (args == null || sender == null) return
        if (args.isEmpty() || args[0].lowercase() == "help") {
            sender.addChatMessage(ChatComponentText(commandUsage))
            return
        }
        if (args.size < 2) {
            sentErrorOccurred("Invalid arguments")
            sender.addChatMessage(ChatComponentText(commandUsage))
            return
        }

        val command = args[0].lowercase()
        val path = args[1]

        if (command !in acceptCommand) {
            sendError("Unknown command: $command")
            sender.addChatMessage(ChatComponentText(commandUsage))
            return
        }

        val realPath = File(configDirectory, path)
        if (!realPath.exists()) {
            sendError("File not found: $path")
            return
        }

        if (command == "run") {
            Thread {
                // ファイルが存在するなら中身を読み、改行で区切り実行
                val lines = realPath.readLines()
                for (line in lines) {
                    if (line.isBlank()) continue
                    if (line.startsWith("#")) continue
                    if (line.startsWith("/say ")) {
                        sendCommand(line.replaceFirst("/say ", ""))
                        continue
                    }
                    if (line.startsWith("/tellraw ")) {
                        sendChat(ChatComponentText("""§7[§6LC-FileManager§7]: ${line.replaceFirst("/tellraw ", "")}"""))
                        continue
                    }
                    if (line.startsWith("\$wait ")) {
                        Thread.sleep(
                            line.replaceFirst("\$wait ", "").toLong()
                        )
                    }
                    sendCommand("""/$line""")
                }
            }.start()
        }
    }
}