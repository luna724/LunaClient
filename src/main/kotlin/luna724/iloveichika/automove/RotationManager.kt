package luna724.iloveichika.automove

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sendChat
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent

/**
プレイヤーの Yaw をいじるクラス
setTargetYaw を呼び出すことで変更を開始できる
 */
class RotationManager {
    private var targetYaw = 0f
    private var startYaw = 0f
    private var ticksRemaining = 0
    private var enableYawChanger = false
    private var instaChange = false


    fun startYawChanger(targetYaw: Float, ticksTaken: Int) {
        val player = Minecraft.getMinecraft().thePlayer
        if (player != null) {
            // Yaw を修正
            val Yaw = if (targetYaw < -180) {
                -180f
            } else if (targetYaw > 180) {
                180f
            } else {
                targetYaw
            }

            sendChat(ChatComponentText(" §r§7[YawChanger]: Changing Yaw to " + (Yaw - 180)))
            this.enableYawChanger = true
            this.startYaw = player.rotationYaw
            this.targetYaw = Yaw
            this.ticksRemaining = ticksTaken
            if (this.ticksRemaining <= 0) this.instaChange = true
        }
    }

    private fun endsYawChanger() {
        sendChat(ChatComponentText(" §r§7[YawChanger]: Changed Yaw to " + (this.targetYaw)))
        this.enableYawChanger = false
    }

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent?) {
        if (!enableYawChanger) {
            return
        }

        if (instaChange) {
            mc.thePlayer?.rotationYaw = targetYaw
            endsYawChanger()
            return
        }
        if (ticksRemaining > 0) {
            val player = mc.thePlayer
            if (player != null) {
                var yawStep = targetYaw - startYaw

                // 差分が180度を超えている場合、逆回転するようにする
                if (yawStep > 180) {
                    yawStep -= 360f
                } else if (yawStep < -180) {
                    yawStep += 360f
                }

                // yawStep の差分 / ticks を行い、少しづつ変更する
                player.rotationYaw = startYaw + (yawStep / ticksRemaining)
                ticksRemaining--
            }
        } else {
            endsYawChanger()
        }
    }
}