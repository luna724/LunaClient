package luna724.iloveichika.automove

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

@SideOnly(Side.CLIENT)
class AutoMove(val settings: Settings, val rotationManager: RotationManager) {
    private val keyBinds: IntArray
        get() {
            /*0=Forward, 1=Backward, 2=Left, 3=Right, 4=Attack*/
            val binds = IntArray(5)
            binds[0] = mc.gameSettings.keyBindForward.keyCode
            binds[1] = mc.gameSettings.keyBindBack.keyCode
            binds[2] = mc.gameSettings.keyBindLeft.keyCode
            binds[3] = mc.gameSettings.keyBindRight.keyCode
            binds[4] = mc.gameSettings.keyBindAttack.keyCode

            return binds
        }

    val movingKey: List<String>
        get() {
            val keyBytes: Int = settings.autoMoveDirection
            val directions: MutableList<String> = ArrayList()

            if ((keyBytes and left) != 0) {
                directions.add("Left")
            }
            if ((keyBytes and right) != 0) {
                directions.add("Right")
            }
            if ((keyBytes and forward) != 0) {
                directions.add("Forward")
            }
            if ((keyBytes and backward) != 0) {
                directions.add("Backward")
            }

            // リストをString配列に変換して返す
            return directions
        }

    // 各移動方向用のフラグ値
    var left: Int = 1 // 0001
    var right: Int = 2 // 0010
    var forward: Int = 4 // 0100
    var backward: Int = 8 // 1000

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent?) {
        // 設定が無効なら
        if (!settings.autoMoveEnabled) {
            return
        }

        // GUIが開いている場合は動作を行わない
        if (mc.currentScreen != null) {
            // TODO: Vigilance GUI など、クライアントサイドGUIを許可する設定を追加

            //System.out.println("Current screen != null!. Current screen: " + mc.currentScreen.toString());
            return
        }
        val player = mc.thePlayer ?: return

        val movingKeys = movingKey
        val keyCodes = keyBinds

        // 値に応じキーを押す
        if (movingKeys.contains("Forward")) {
            KeyBinding.setKeyBindState(keyCodes[0], true)
            KeyBinding.onTick(keyCodes[0])
        }
        if (movingKeys.contains("Backward")) {
            KeyBinding.setKeyBindState(keyCodes[1], true)
            KeyBinding.onTick(keyCodes[1])
        }
        if (movingKeys.contains("Left")) {
            KeyBinding.setKeyBindState(keyCodes[2], true)
            KeyBinding.onTick(keyCodes[2])
        }
        if (movingKeys.contains("Right")) {
            KeyBinding.setKeyBindState(keyCodes[3], true)
            KeyBinding.onTick(keyCodes[3])
        }

        if (settings.autoMoveClickEnable) {
            KeyBinding.setKeyBindState(keyCodes[4], true)
        }
    }

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load?) {
        /* ワールドがロードされたら動作停止 */
        if (!settings.autoMoveStopWhenServerSwap) {
            return
        }

        if (settings.autoMoveEnabled) {
            println("Stopped AutoMoving by Safety Module.")
            settings.autoMoveEnabled = false
            stopAutoMove()
        }
    }
}