package luna724.iloveichika.binsniper.ah

import luna724.iloveichika.binsniper.BinSniper
import luna724.iloveichika.lunaclient.sendChat
import net.minecraft.util.ChatComponentText

/**
 * インスタンス化禁止
 */
class BinSnipeSession {
    private var isEnabled: Boolean = false

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
    fun start(customMessage: String? = "§7Started BinSniper") {
        setEnable(true)

        if (customMessage != null) {
            sendChat(
                ChatComponentText(
                    BinSniper.HEADER + customMessage
                )
            )
        }
    }

    /**
     * AutoGarden をユーザーが認識できる形式で停止する関数
     */
    fun stop(customMessage: String? = "§7Stopped BinSniper") {
        setEnable(false)

        if (customMessage != null) {
            sendChat(
                ChatComponentText(
                    BinSniper.HEADER + customMessage
                )
            )
        }
    }
}