package luna724.iloveichika.gardening.antiantimacro

import luna724.iloveichika.gardening.Gardening.Companion.playerPosUtil
import luna724.iloveichika.gardening.Gardening.Companion.session
import luna724.iloveichika.lunaclient.sendChat
import luna724.iloveichika.lunaclient.sentErrorOccurred
import luna724.iloveichika.lunaclient.utils.playSound
import luna724.iloveichika.lunaclient.utils.showPrimaryTextWindow
import net.minecraft.network.play.server.S08PacketPlayerPosLook
import net.minecraft.util.ChatComponentText
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import kotlin.math.abs

class TeleportationPacketHandler {
    private val packetTimestamps = mutableListOf<Long>()

    fun onPacketReceived(packet: S08PacketPlayerPosLook, ci: CallbackInfo): Boolean {
        val success: Boolean = processPacket(packet, ci) ?: return false

        // 現状は false を絶対返す
        return false
    }

    /**
     * パケットを処理する、
     * パケットを処理せず終わるとき(拒否)は True、
     * クライアントに適用させるときは False を返す
     */
    private fun processPacket(packet: S08PacketPlayerPosLook, ci: CallbackInfo): Boolean {
        // TODO: 正式実装
        //        sendChat(
//            ChatComponentText("S08PacketPlayerPosLook received on kotlin!")
//        )
        if (!session.isEnable()) {
            return false
        }

        // パケットの値を取得
        val atX = packet.x
        val atY = packet.y
        val atZ = packet.z
        val atYaw = packet.yaw
        val atPitch = packet.pitch

        val currentXYZ = playerPosUtil.getPlayerPosition(4)
        val currentRotation = playerPosUtil.getPlayerRotation(1)
        val x = currentXYZ[0]
        val y = currentXYZ[1]
        val z = currentXYZ[2]
        val yaw = currentRotation[0]
        val pitch = currentRotation[1]

        // 誤差が 0.1 block以下かつ、過去2秒間に 10回以下の Packet を受け取っていたら無視
        val toleranceX = abs(atX - x)
        val toleranceY = abs(atY - y)
        val toleranceZ = abs(atZ - z)
        val toleranceYaw = abs(atYaw - yaw)
        val tolerancePitch = abs(atPitch - pitch)

        if (toleranceX <= 0.1 && toleranceY <= 0.1 && toleranceZ <= 0.1 && toleranceYaw <= 0.1 && tolerancePitch <= 0.1) {
            val currentTime = System.currentTimeMillis()

            // 2 秒以内のパケット数を確認
            packetTimestamps.add(currentTime)
            packetTimestamps.removeIf { timestamp -> currentTime - timestamp > 2000 }

            if (packetTimestamps.size <= 10) {
//                sendChat(ChatComponentText(
//                    "[!!IGNORE THIS!!] S08PacketPlayerPosLook received. (maybe admin-check) [!!IGNORE THIS!!]"))
                return false
            }
            else {
//                sendChat(
//                    ChatComponentText(
//                        "§l§o§4[!!READ THIS!!] received many S08PacketPlayerPosLook! (10+ in 2000ms). Enabling blink.. §r§c(React if it looks like you need it!)"
//                    ))
                // TODO: enable blink
                return false
            }
        }
        else {
            // 誤差が大きい場合
//            sendChat(
//                ChatComponentText(
//                    "received visual-anti-macro packet. Enabling blink and sending Notification.."
//                )
//            )
//            sentErrorOccurred(
//                "[Anti-Macro-Check]: a player received visual-anti-macro packet! (tolerance: $toleranceX, $toleranceY, $toleranceZ, $toleranceYaw, $tolerancePitch)",
//                report = true
//            )
//            showPrimaryTextWindow("LunaClient / Anti-AntiMacro", "You got \"hello, macro check!\"! react on Minecraft!")

//            playSound("note.pling", 1.0f, 1.5f)
            return true
        }
    }
}