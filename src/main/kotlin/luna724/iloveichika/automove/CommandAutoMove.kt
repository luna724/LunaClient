package luna724.iloveichika.automove

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText

class CommandAutoMove(private val autoMove: AutoMove) : CommandBase() {
    private val helpMessage: String
        get() = """
            §f[§dLC-AutoMove§f] Help
            §6 - §f/automove toggle
            §e AutoMove の切り替え
            §6 - §f/automove setdirection <L/R/F/B>
            §e 歩く向きの設定 L = 左, R = 右, F = 前, B = 後
            §6 - §f/automove hoverclick
            §e 左クリックするかどうか
            """.trimIndent()

    override fun getCommandName(): String {
        return "automove"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/automove <toggle|setdirection|hoverclick> {L/R/F}"
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.size < 1) {
            val helpMessage = helpMessage
            sender.addChatMessage(ChatComponentText(helpMessage))
            return
        }
        if ("help".equals(args[0], ignoreCase = true)) {
            val helpMessage = helpMessage
            sender.addChatMessage(ChatComponentText(helpMessage))
            return
        } else if ("start".equals(args[0], ignoreCase = true)) {
            autoMove.settings.autoMoveEnabled = true

            val msg = "[§dLC-AutoMove§f]: §6AutoMove Started."
            sender.addChatMessage(ChatComponentText(msg))
        } else if ("stop".equals(args[0], ignoreCase = true)) {
            autoMove.settings.autoMoveEnabled = false

            val msg = "[§dLC-AutoMove§f]: §6AutoMove Stopped."
            stopAutoMove()
            sender.addChatMessage(ChatComponentText(msg))
        } else if ("toggle".equals(args[0], ignoreCase = true)) {
            autoMove.settings.autoMoveEnabled = !autoMove.settings.autoMoveEnabled
            val msg: String
            if (autoMove.settings.autoMoveEnabled) {
                msg = "[§dLC-AutoMove§f]: §6AutoMove Started."
            } else {
                msg = "[§dLC-AutoMove§f]: §6AutoMove Stopped."
                stopAutoMove()
            }
            sender.addChatMessage(ChatComponentText(msg))
        } else if ("hoverclick".equals(args[0], ignoreCase = true)) {
            autoMove.settings.autoMoveClickEnable = !autoMove.settings.autoMoveClickEnable
            val msg = if (autoMove.settings.autoMoveClickEnable) {
                "[§dLC-AutoMove§f]: §6hoverClick Enabled."
            } else {
                "[§dLC-AutoMove§f]: §6hoverClick Disabled."
            } //      sender.addChatMessage(new ChatComponentText(msg));
            sender.addChatMessage(ChatComponentText(msg))
        } else if ("setdirection".equals(args[0], ignoreCase = true)) {
            if (args.size < 2) {
                autoMove.settings.autoMoveDirection = 0
                val msg = "[§dLC-AutoMove§f]: §6Changed direction to §a§lNaN"
                sender.addChatMessage(ChatComponentText(msg))
            } else {
                changeDirection(args[1])
            }
        } else if ("safemode".equals(args[0], ignoreCase = true)) {
            autoMove.settings.autoMoveStopWhenServerSwap = !autoMove.settings.autoMoveStopWhenServerSwap

            val msg = "[§dLC-AutoMove§f]: §6Safemode: " + autoMove.settings.autoMoveStopWhenServerSwap
            sender.addChatMessage(ChatComponentText(msg))
        } else if ("toggleinfo".equals(args[0], ignoreCase = true)) {
            autoMove.settings.autoMoveInfo = !autoMove.settings.autoMoveInfo
            var msg = "[§dLC-AutoMove§f]: §6Internal Info: "

            msg += if (autoMove.settings.autoMoveInfo) {
                "§lActive"
            } else {
                "§lDisable"
            }
            sender.addChatMessage(ChatComponentText(msg))
        } else if ("setyaw".equals(args[0], ignoreCase = true)) {
            if (args.size < 2) {
                val msg = "[§dLC-AutoMove§f]: §cmissing 1 Required arguments. §f: `/automove setyaw <Yaw>`"
                sender.addChatMessage(ChatComponentText(msg))
                return
            }
            val stringYaw = args[1]
            val yaw = stringYaw.toFloat()
            autoMove.rotationManager.startYawChanger(yaw, 25)
        } else if (args.size >= 2) {
            return
        }

        autoMove.settings.saveConfig()
    }

    override fun addTabCompletionOptions(sender: ICommandSender, args: Array<String>, pos: BlockPos): List<String>? {
        if (args.size == 1) {
            // 第一引数の補完を提供
            return getListOfStringsMatchingLastWord(
                args,
                "help",
                "start",
                "stop",
                "toggle",
                "setyaw",
                "setdirection",
                "hoverclick",
                "safemode",
                "toggleinfo"
            )
        } else if (args.size == 2) {
            // setdirection時に 第二引数の補完を提供
            if (args[0].equals("setdirection", ignoreCase = true)) {
                return getListOfStringsMatchingLastWord(args, "reset", "l", "r", "f", "b", "rf", "lf", "br", "bf")
            }
            return getListOfStringsMatchingLastWord(args, "senddatatolunaclient")
        }
        return null
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0 // 権限レベル0（すべてのプレイヤーが使用可能）
    }

    companion object {
    }
}