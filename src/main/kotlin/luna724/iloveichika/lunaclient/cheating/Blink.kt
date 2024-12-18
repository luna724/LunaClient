package luna724.iloveichika.lunaclient.cheating

import net.minecraft.client.Minecraft
import net.minecraft.network.Packet
import net.minecraft.network.play.client.C03PacketPlayer
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue

class Blink {
    private val mc = Minecraft.getMinecraft()
    private val packetQueue = ConcurrentLinkedQueue<Packet<*>>() // パケットを保存するキュー
    private var isEnabled = false

    // Blinkを有効化
    fun enableBlink() {
        isEnabled = true
    }

    // Blinkを無効化
    fun disableBlink() {
        isEnabled = false
        sendAllPackets() // 保存されているパケットを全て送信
    }

    // パケットの処理
    fun onSendPacket(packet: Packet<*>) {
        if (isEnabled && packet is C03PacketPlayer) {
            // C03PacketPlayerの場合は保存して送信を防ぐ
            packetQueue.add(packet)
        } else {
            // 通常のパケット送信
            mc.netHandler.addToSendQueue(packet)
        }
    }

    // 保存されているパケットを送信
    private fun sendAllPackets() {
        while (packetQueue.isNotEmpty()) {
            val packet = packetQueue.poll()
            mc.netHandler.addToSendQueue(packet)
        }
    }

    // 一定期間後にパケットを送信する (オプション機能)
    @OptIn(DelicateCoroutinesApi::class)
    fun flushPacketsAfterDelay(delayMillis: Long) {
        GlobalScope.launch {
            delay(delayMillis)
            if (isEnabled) {
                sendAllPackets()
            }
        }
    }
}