package luna724.iloveichika.automove

import luna724.iloveichika.automove.AutoMoveMod.Companion.autoMoveInstance
import luna724.iloveichika.automove.AutoMoveMod.Companion.autoMoveSettings
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.lunaclient.sendChatError
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraft.client.settings.KeyBinding
import net.minecraft.util.ChatComponentText
import java.util.*

private fun containsOnlyAvailableChars(input: String, availableChar: List<String>): Boolean {
    for (c in input.lowercase(Locale.getDefault()).toCharArray()) {
        if (!availableChar.contains(c.toString())) {
            return false
        }
    }
    return true
}

/**
 *
 *             /automove setdirection <l/r/f/b/..>
 *             /automove setdirection <l/r/f/b> 通常状態
 *             /automove setdirection lrf 左右前
 *            ** /automove setdirection は非対応 **
 *
 */
fun changeDirection(
    direction: String
) {
    autoMoveInstance.settings.autoMoveDirection = 0
    val key = direction.lowercase(Locale.getDefault())
    if (key.equals("reset", ignoreCase = true) || key.isEmpty()) {
        val msg = " [§dLC-AutoMove§f]: §6direction removed."
        sendChat(
            ChatComponentText(msg)
        )
    }

    val availableChar: List<String> = mutableListOf("f", "b", "r", "l")
    if (!containsOnlyAvailableChars(key, availableChar)) {
        val msg = " [§dLC-AutoMove§f]: §cUnknown args§f: $key"
        sendChatError(msg)
        return
    }

    // あるならそれに変換する
    if (key.contains("l")) autoMoveInstance.settings.autoMoveDirection =
        autoMoveInstance.settings.autoMoveDirection or autoMoveInstance.left
    if (key.contains("r")) autoMoveInstance.settings.autoMoveDirection =
        autoMoveInstance.settings.autoMoveDirection or autoMoveInstance.right
    if (key.contains("f")) autoMoveInstance.settings.autoMoveDirection =
        autoMoveInstance.settings.autoMoveDirection or autoMoveInstance.forward
    if (key.contains("b")) autoMoveInstance.settings.autoMoveDirection =
        autoMoveInstance.settings.autoMoveDirection or autoMoveInstance.backward

    val moveDirections = autoMoveInstance.movingKey
    val msg = " [§dLC-AutoMove§f]: §6Changed direction to §a§l$moveDirections"
    println(msg)
    sendChat(
        ChatComponentText(msg)
    )
}

private val keyBinds: IntArray
    get() {
        // SAME Methods as Automove.keyBinds
        //TODO: 分割されている子のメゾットを統合する
        val binds = IntArray(5)
        binds[0] = mc.gameSettings.keyBindForward.keyCode
        binds[1] = mc.gameSettings.keyBindBack.keyCode
        binds[2] = mc.gameSettings.keyBindLeft.keyCode
        binds[3] = mc.gameSettings.keyBindRight.keyCode
        binds[4] = mc.gameSettings.keyBindAttack.keyCode

        return binds
    }

fun stopAutoMove() {
    println("Stopping AutoMoving..")
    val keyCodes = keyBinds

    KeyBinding.setKeyBindState(keyCodes[0], false)
    KeyBinding.setKeyBindState(keyCodes[1], false)
    KeyBinding.setKeyBindState(keyCodes[2], false)
    KeyBinding.setKeyBindState(keyCodes[3], false)
    KeyBinding.setKeyBindState(keyCodes[4], false)
}

fun startAutoMove() {
    autoMoveSettings.autoMoveEnabled = true
}