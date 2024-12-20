package luna724.iloveichika.gardening.commands

import luna724.iloveichika.gardening.main.checkDirectionsCorrectly
import luna724.iloveichika.gardening.main.getCurrentRotation
import luna724.iloveichika.gardening.main.getCurrentXYZ
import luna724.iloveichika.gardening.util.SessionOpt
import luna724.iloveichika.gardening.util.addSessionOpt
import luna724.iloveichika.gardening.util.loadSessionOpt
import luna724.iloveichika.gardening.util.removeSessionOpt
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

    private fun makeRestore(base: ChatComponentText, sessionOptValue: SessionOpt): IChatComponent {
        val command = "/"
        val restore = ChatComponentText("[Restore Currently NOT Supported]").apply {
            chatStyle = ChatStyle()
                .setColor(EnumChatFormatting.GREEN)
                .setChatClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, command))
                .setChatHoverEvent(HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponentText("Click to run $command")))
        }

        base.appendSibling(restore)
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
        )
        )
        val baseMsg = ChatComponentText("$HEADER§aSaved as §l${key}§r. §7(XYZ: ${xyz}, Rotation: ${rotation}, Direction: ${direction}) ")
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

        val targetKey = args[1]
        val sessionOpt = loadSessionOpt() // 現在のSessionOptを取得

        // 対象キーが存在しない場合のエラー処理
        if (!sessionOpt.containsKey(targetKey)) {
            sendError("Key '$targetKey' does not exist.")
            return
        }

        // キーの値を保持
        val sessionOptValue: SessionOpt = sessionOpt[targetKey]!!

        // キーを削除
        removeSessionOpt(targetKey)

        // 削除完了のメッセージを送信
        val baseMsg = ChatComponentText("$HEADER§aDeleted §l$targetKey§r.")
        sender.addChatMessage(
            //makeRestore(baseMsg, sessionOptValue)
            baseMsg
        )
    }

    fun listXYZ(
        sender: ICommandSender, args: Array<String>
    ) {
        val sessionOpt = loadSessionOpt() // 現在のSessionOptを取得

        if (sessionOpt.isEmpty()) {
            // 登録データがない場合の処理
            sender.addChatMessage(
                ChatComponentText("$HEADER§aAll XYZ in the current presets: §6None")
            )
            return
        }

        // ヘッダーを表示
        sender.addChatMessage(
            ChatComponentText("$HEADER§aAll XYZ in current presets")
        )
        sender.addChatMessage(
            ChatComponentText("§7")
        ) // デザイン的な分離用の空行のような表示

        // 各キーをループして処理
        sessionOpt.forEach { (key, value) ->
            val xyz = value.coordinates
            val rotation = value.orientation
            val direction = value.direction

            // XYZ, 回転値、データを取り出す
            val x = xyz[0]
            val y = xyz[1]
            val z = xyz[2]
            val yaw = rotation[0]

            // 全体メッセージを作成
            val baseMsg = ChatComponentText("§7[§e$key§7]: §r§7(X: §f$x§7, Y: §f$y§7, Z: §f$z§7, Yaw: §f$yaw§7, Direction: §2$direction§7) ")

            // チャットにメッセージを送信
            sender.addChatMessage(makeRemove(baseMsg, key))
        }
    }

    fun currentXYZ(
        sender: ICommandSender, args: Array<String>
    ) {
        val currentXYZ = getCurrentXYZ(1) ?: run {
            sendError("Failed to retrieve current XYZ.")
            return
        }

        val sessionOpt = loadSessionOpt() // 現在のSessionOptを取得
        var triggeredKey: String? = null

        // 現在の座標と保存された座標を比較して一致するものを探す
        sessionOpt.forEach { (key, value) ->
            if (value.coordinates == currentXYZ) {
                triggeredKey = key
                return@forEach
            }
        }

        // 一致するキーが見つからなかった場合
        triggeredKey ?: run {
            sender.addChatMessage(
                ChatComponentText("$HEADER§9Triggered XYZ hasn't been found!")
            )
            return
        }

        // 一致するキーが見つかった場合、その詳細を表示
        val keyValue = sessionOpt[triggeredKey]!!
        val xyz = keyValue.coordinates
        val rotation = keyValue.orientation
        val direction = keyValue.direction

        val x = xyz[0]
        val y = xyz[1]
        val z = xyz[2]
        val yaw = rotation[0]

        val baseMsg = ChatComponentText(
            "$HEADER§9First Triggered: §7[§a$triggeredKey§7]: §r§7(X: §f$x§7, Y: §f$y§7, Z: §f$z§7, Yaw: §f$yaw§7, Direction: §2$direction§7)"
        )

        // `[Remove]`リンクを追加して送信
        sender.addChatMessage(makeRemove(baseMsg, triggeredKey!!))
    }
}