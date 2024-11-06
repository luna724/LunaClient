package luna724.iloveichika.automove

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
            val mc = Minecraft.getMinecraft()
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

            if ((keyBytes and DIRECTION_LEFT) != 0) {
                directions.add("Left")
            }
            if ((keyBytes and DIRECTION_RIGHT) != 0) {
                directions.add("Right")
            }
            if ((keyBytes and DIRECTION_FORWARD) != 0) {
                directions.add("Forward")
            }
            if ((keyBytes and DIRECTION_BACKWARD) != 0) {
                directions.add("Backward")
            }

            // リストをString配列に変換して返す
            return directions
        }

    // 各移動方向用のフラグ値
    var DIRECTION_LEFT: Int = 1 // 0001
    var DIRECTION_RIGHT: Int = 2 // 0010
    var DIRECTION_FORWARD: Int = 4 // 0100
    var DIRECTION_BACKWARD: Int = 8 // 1000
    val Identifier: String = "§7[§dLunaAPI§7]:§7§f "

    fun AutoMovingStop() {
        println("Stopping AutoMoving..")
        val keyCodes = keyBinds

        KeyBinding.setKeyBindState(keyCodes[0], false)
        KeyBinding.setKeyBindState(keyCodes[1], false)
        KeyBinding.setKeyBindState(keyCodes[2], false)
        KeyBinding.setKeyBindState(keyCodes[3], false)
        KeyBinding.setKeyBindState(keyCodes[4], false)
    }

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent?) {
        // 設定が無効なら
        if (!settings.autoMoveEnabled) {
            return
        }

        val mc = Minecraft.getMinecraft()
        // GUIが開いている場合は動作を行わない
        if (mc.currentScreen != null) {
            //System.out.println("Current screen != null!. Current screen: " + mc.currentScreen.toString());
            return
        }
        val player = Minecraft.getMinecraft().thePlayer ?: return

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

        println("Stopped AutoMoving by Safety Module.")
        settings.autoMoveEnabled = false
        AutoMovingStop()
    }

    fun sendDataToLunaClient(request: String, args: Array<String>): String {
        return ""
    }
}