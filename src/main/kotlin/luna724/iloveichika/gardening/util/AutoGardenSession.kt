package luna724.iloveichika.gardening.util;

import luna724.iloveichika.automove.stopAutoMove
import luna724.iloveichika.gardening.Gardening
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.lunaclient.sendChat
import net.minecraft.util.ChatComponentText

/**
 * インスタンス化禁止
 * Gardening.kt companion 内の session のみを使用する
 */
class AutoGardenSession {
    /**
     * AutoGardenが有効か否か
     */
    private var isEnabled: Boolean = false;

    /**
     * isEnabled を変更するだけ
     */
    private fun setEnable(enable: Boolean) {
        isEnabled = enable
    }

    /**
     * AutoGarden が有効かどうかを返す
     */
    fun isEnable(): Boolean {
        return isEnabled
    }

    /**
     * AutoGarden をユーザーが認識できる形式で開始する関数
     */
    fun start(customMessage: String? = "§7Started AutoGarden") {
        setEnable(true)
        stopAutoMove()

        if (customMessage != null) {
            sendChat(
                ChatComponentText(
                    Gardening.HEADER + customMessage
                )
            )
        }
    }

    /**
     * AutoGarden をユーザーが認識できる形式で停止する関数
     */
    fun stop(customMessage: String? = "§7Stopped AutoGarden") {
        setEnable(false)
        stopAutoMove()

        if (customMessage != null) {
            sendChat(
                ChatComponentText(
                    Gardening.HEADER + customMessage
                )
            )
        }
    }
}