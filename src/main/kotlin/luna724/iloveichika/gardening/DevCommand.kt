package luna724.iloveichika.gardening

import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.gardening.dev.ManageXYZ
import luna724.iloveichika.gardening.util.LoadOfficialPresets
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import scala.xml.Null
import kotlinx.coroutines.Dispatchers
import java.util.*

class DevCommand : CommandBase() {
    private val commandName: String = "lcg_cli"

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }

    override fun getCommandName(): String {
        return commandName
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return "/$commandName (see wiki for more information) https://luna724.github.io/repo/LunaClient/docs/commands/lcg_cli"
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        try {
            val see_wiki = " (see wiki for more information) https://luna724.github.io/repo/LunaClient/docs/commands/lcg_cli"
            if (args.isEmpty()) {
                sendChat(
                    ChatComponentText(getCommandUsage(null))
                )
                return
            }
            val trigger: String = args[0].lowercase(Locale.getDefault())
            val manageXYZ = ManageXYZ()

            if (trigger.equals("import_official", ignoreCase = true)) {
                val key: String? = args.getOrNull(1)?.lowercase()
                var cloud_key: String? = null
                if (key == "from_cloud") {
                    cloud_key = args.getOrNull(2)?.lowercase()
                }
                if (key.isNullOrEmpty() || (cloud_key.isNullOrEmpty() && key == "from_cloud")) {
                    // key がない場合
                    sender.addChatMessage(
                        ChatComponentText("Unknown key.$see_wiki")
                    )
                    return
                }
                val loadOfficial = LoadOfficialPresets()
                val keysValidate = loadOfficial.validateLocalKey(key)
                if (keysValidate) {
                    // ローカルから読み込み
                    val success = loadOfficial.loadLocal(key)
                    if (success == true) {
                        sender.addChatMessage(
                            ChatComponentText("Import successful. (try /lcg listxyz)")
                        )
                        return
                    }
                    else {
                        sentErrorOccurred("Import Failed from Local. ($key)")
                        return
                    }
                }
                else if (key == "from_cloud") {
                    sender.addChatMessage(
                        ChatComponentText("Trying to get Cloud template..")
                    )
                    val success = loadOfficial.loadCloud(cloud_key!!)
                    if (success == true) {
                        sender.addChatMessage(
                            ChatComponentText("Import successful. (try /lcg listxyz)")
                        )
                        return
                    }
                    else {
                        sentErrorOccurred("Import Failed from Cloud. ($cloud_key)")
                    }
                }
            }
        }
        catch (e: Throwable) {
            throw Exception("Error in command processing \"/lcg\"", e)
        }
    }
}