package luna724.iloveichika.gardening

import luna724.iloveichika.automove.RotationManager
import luna724.iloveichika.automove.changeDirection
import luna724.iloveichika.automove.startAutoMove
import luna724.iloveichika.automove.stopAutoMove
import luna724.iloveichika.gardening.Gardening.Companion.adminConfig
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.gardening.main.*
import luna724.iloveichika.gardening.util.SessionOpt
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class AutoGarden {
    /**
     * onTick用の特別関数
     * プレイヤーの向き、視点変更を行う
     */
    private fun swapMovement(
        yaw: Double, pitch: Double, movements: String
    ) {
        Thread { //TODO: ANtiAntiMacroチェックのいくつかはここに追加される
            // いったん止めて作動させる
            stopAutoMove()

            // 移動方向、視点の向きを変換
            changeDirection(movements)
            RotationManager().startYawChanger(yaw.toFloat(), adminConfig.yawChangingTime)

            startAutoMove()
        }.start()
    }

    private var triggeredTime: Long? = null
    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        // AutoGardenがオフなら終わる
        if (!session.isEnable()) return

        // 次回のトリガーまでは 500ms は待機する
        if (triggeredTime != null && System.currentTimeMillis() - triggeredTime!! < 500) return

        // XYZの取得
        val currentXYZ: List<Double> = getCurrentXYZ() ?: listOf(0.0, -1.0, 0.0)
        val sessionOpt: LinkedHashMap<String, SessionOpt>? = getSessionOption()
        if (sessionOpt == null) {
            sentErrorOccurred("SessionOption is Null!")
            return
        }

        // sessionOpt を座標リストに変換
        val xyzLists: List<List<Double>> = convertSessionOptToXYZLists(sessionOpt)
        val (matched, index) = checkXYZisIn(xyzLists, currentXYZ)
        if (!matched) return

        // マッチした場合、マッチした sessionOpt を取得し、それらに基づき処理を実行する
        val matchedSessionOpt = sessionOpt.entries.toList()[index].value
        val rotations = matchedSessionOpt.orientation
        val yaw = rotations[0]
        val pitch = rotations[1]
        // 移動方向が不明な場合、reset として処理
        val movements: String = checkDirectionsCorrectly(matchedSessionOpt.direction) ?: "reset"

        // すべての条件が揃ったら、トリガーを実行
        swapMovement(yaw, pitch, movements)
        triggeredTime = System.currentTimeMillis()
    }
}