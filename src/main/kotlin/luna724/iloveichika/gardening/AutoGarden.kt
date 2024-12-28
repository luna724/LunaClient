package luna724.iloveichika.gardening

import luna724.iloveichika.automove.RotationManager
import luna724.iloveichika.automove.changeDirection
import luna724.iloveichika.automove.startAutoMove
import luna724.iloveichika.automove.stopAutoMove
import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.playerPosUtil
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.gardening.Gardening.Companion.sessionOptionUtil
import luna724.iloveichika.gardening.util.SessionOpt
import luna724.iloveichika.lunaclient.LunaClient.Companion.configManager
import luna724.iloveichika.lunaclient.sendCommand
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

/**
 * Gardening以外からのインスタンス化禁止
 */
class AutoGarden {
    private fun changeAutoMoveDirection(movements: String) {
        if (movements == "spawn") {
            // スポーンの場合、コマンドを送信して終わる
            sendCommand("/warp garden")
            return
        }
        changeDirection(movements)
    }

    /**
     * AutoGarden トリガー時の要素
     *
     */
    private fun swapMovement(
        yaw: Double, pitch: Double, movements: String
    ) { //TODO: ANtiAntiMacroチェックのいくつかはここに追加される
        // いったん止めて作動させる
        stopAutoMove()

        // 移動方向、視点の向きを変換
        changeAutoMoveDirection(movements)
        rotationManager.startYawChanger(yaw.toFloat(), adminConfig.yawChangingTime)

        startAutoMove()
    }

    /**
     * 座標チェックを行う
     *
     * @return: 座標が SessionOptions に含まれていたか否か
     */
    private fun triggerCheck(): Pair<Boolean, Int> {
        val currentXYZ: List<Double> = playerPosUtil.getPlayerPosition(2)
        val sessionOption: LinkedHashMap<String, SessionOpt> = sessionOptionUtil.loadSessionOption()
        val xyzLists: List<List<Double>> = sessionOptionUtil.convertSessionOptionToCoordLists(sessionOption)
        return playerPosUtil.isXYZIn(xyzLists, currentXYZ)
    }

    companion object {
        // 前回トリガーした時間を保持
        private var triggeredTime: Long? = null

        // 次回のトリガーまでの待機時間 (デフォで500ms)
        private val triggeredCooldown: Float = configManager.config?.autoGardenCategory?.autoGarden?.movementCooldown ?: 500f

        private val rotationManager = RotationManager()
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (!session.isEnable()) return // AutoGardenが無効
        if (
            triggeredTime != null && System.currentTimeMillis() - triggeredTime!! < triggeredCooldown
        ) return // クールダウンが終わる前に呼び出している

        val sessionOption: LinkedHashMap<String, SessionOpt> = sessionOptionUtil.loadSessionOption()
        val (triggered, triggeredIndex) = triggerCheck()
        if (!triggered) return // トリガーしなかった場合

        val triggeredSessionOpt: SessionOpt = sessionOption.values.toList()[triggeredIndex]
        // いろいろ抽出
        val orientation = triggeredSessionOpt.orientation
        val movements = triggeredSessionOpt.direction

        // トリガー
        swapMovement(orientation[0], orientation[1], movements)
        triggeredTime = System.currentTimeMillis()
    }
}