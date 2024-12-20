package luna724.iloveichika.gardening.main

import luna724.iloveichika.lunaclient.sendChat
import net.minecraft.util.ChatComponentText

/**
 * 処理する方向があって要るかどうかを確認する
 * rlfb の文字列、または reset, spawn 出ない場合は null を返す
 */
fun checkDirectionsCorrectly(direction: String): String? {
    // setxyz <trigger> に渡される Direction を正則化する
    val availableChar = "rlfb"
    val lowerDirection = direction.lowercase()

    if (lowerDirection == "reset") {
        return "reset"
    }
    if (lowerDirection == "spawn") {
        return "spawn"
    }

    val regex = Regex("^[${availableChar}]+$")
    if (!regex.matches(lowerDirection)) {
        sendChat(ChatComponentText("\"direction\" can only contains l, r, f, b, \"reset\" and \"spawn\". but got $lowerDirection"))
        return null
    }
    return lowerDirection
}