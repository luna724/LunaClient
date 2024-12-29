package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.Gardening.Companion.playerPosUtil
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.LunaClient.Companion.config
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sendCommand
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class AntiAntiMacro {
    companion object {
        // XYZ Checker用変数
        private var previousXYZ: List<Double> = listOf(0.0, -1.0, 0.0)
        private var previousXYZTriggeredTime: Long? = System.currentTimeMillis()
        private val xyzCheckerDelay: Long = config.autoGardenCategory.antiAntiMacroConfig.xyzTrackerAcceptableTimeMS.toLong() ?: 5000L

    }

    /**
     * AntiAntiMacroがトリガーした際の停止用関数
     */
    private fun triggered(message: String = "") {
        if (!session.isEnable()) return
        session.stop(
            "§cStopped AutoGarden by §4Anti-AntiMacro §r§7(§6$message§7)"
        )
        println("Anti-AntiMacro triggered!")

        val command = config.autoGardenCategory.antiAntiMacroConfig.antiAntiMacroCommand
        if (command.isNotEmpty()) {
            sendCommand(command)
        }
    }

    /**
     * 座標によるチェックを行う
     * xyzCheckerDelay を超える座標の重複がみられた場合、AutoGardenを停止する
     */
    private fun xyzChecker() {
        val nowXYZ = playerPosUtil.getPlayerPosition(12)
        val currentTime = System.currentTimeMillis()
        val previousTime = previousXYZTriggeredTime ?: currentTime // 初期値を上書きし、即座にトリガーしないようにする

        if (playerPosUtil.compareXYZ(
            nowXYZ, previousXYZ // XYZ が一致するかのチェック
        )) {
            if (currentTime - previousTime >= xyzCheckerDelay) {
                // XYZが一致、かつdelayを上回った場合、トリガーする
                triggered("XYZChecker")
            }
        }
    }


    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        // AutoGarden が無効、AntiAntiMacroが無効、プレイヤーが不明な場合は停止
        if (!session.isEnable()) return
        if (!config.autoGardenCategory.antiAntiMacroConfig.antiAntiMacroMainToggle) return
        if (mc.thePlayer == null) return

        xyzChecker()
    }

    /**
     * AutoGarden 側で trigger した場合のフック関数
     */
    fun whenAutoGardenTriggered(
        yaw: Double, pitch: Double, movements: String
    ): Boolean { // トリガーをキャンセルするかどうかを返す
        // TODO: 一定時間トリガーしなかった際の停止処理などを追加
        return false
    }
}