package luna724.iloveichika.gardening.main

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.utils.generateUniqueBase64Key
import net.minecraft.command.ICommandSender
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent

class ManageXYZ {
    private val HEADER: String = "§6[§2ManageXYZ§6]§f: "
    /**コマンド実行失敗*/
    private fun sendError(e: String) {
        mc.thePlayer.addChatComponentMessage(
            ChatComponentText(
                HEADER+e
            )
        )
    }

    fun makeRemove(base: ChatComponentText, key: String): IChatComponent {
        val command = "/lcg removexyz ${key}"
        val remove = ChatComponentText("[Remove]").apply {
            chatStyle = ChatStyle()
                .setColor(EnumChatFormatting.RED)
                .setChatClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, command))
                .setChatHoverEvent(HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponentText("Click to run $command")))
        }

        base.appendSibling(
            remove
        )
        return base
    }

    /**# setXYZ
     * `/lcg setxyz <direction> (ignoreY/changePitch)`
     * - direction: 方向指定
     * - ignoreY/changePitch: オプショナルで追加、名前の通りの処理を行う
     *
     * ignoreY の場合、 XYZ の Y を -1 に
     * changePitchは sessionOpt:chancePitch の値を変更する
     */
    fun setXYZ(
        sender: ICommandSender, args: Array<String>
    ) {
        if (args.size < 2) { // 小さい
            sendError("setXYZ requires a positional argument (/lcg setxyz <direction>)")
            return
        }
        // 条件がそろった場合、setXYZ.py を呼び出す

        val direction: String = checkDirectionsCorrectly(args[1]) ?: return
        var ignoreY = false
        var changePitch = false

        if (args.any { it.equals("ignorey", ignoreCase = true) }) ignoreY = true
        if (args.any { it.equals("changepitch", ignoreCase = true) }) changePitch = true
        val key: String = generateUniqueBase64Key(
            loadSessionOpt().keys
        )
        val xyz:MutableList<Double> = getCurrentXYZ(1)?.toMutableList() ?: run {
            sendError("Exception in getCurrentXYZ()")
            return
        }
        val rotation = getCurrentRotation(1) ?: run {
            sendError("Exception in getCurrentRotation()")
            return
        }

        if (ignoreY) {
            xyz[1] = -1.0 // Y を -1 に
        }

        addSessionOpt(key, SessionOpt(
            xyz, rotation, direction, changePitch
        ))
        val baseMsg = ChatComponentText("§aSaved as §l${key}§r. §7(XYZ: ${xyz}, Rotation: ${rotation}, Direction: ${direction}) ")
        sender.addChatMessage(
            makeRemove(baseMsg, key)
        )
    }

    fun removeXYZ(
        sender: ICommandSender, args: Array<String>
    ) {
        if (args.size < 2) {
            sendError("removeXYZ requires a positional argument (/lcg removexyz <targetKey>)")
            return
        }
    }

    fun listXYZ(
        sender: ICommandSender, args: Array<String>
    ) {
        return
    }

    fun currentXYZ(
        sender: ICommandSender, args: Array<String>
    ) {
        return
    }
}