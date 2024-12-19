package luna724.iloveichika.lunaclient.cheating

import kotlinx.coroutines.*
import net.minecraft.client.Minecraft
import net.minecraft.network.Packet
import java.util.concurrent.ConcurrentLinkedQueue

class PacketHandler {
    private val mc = Minecraft.getMinecraft()
    private val packetQueue = ConcurrentLinkedQueue<Pair<Packet<*>, Long>>()
    private var isRunning = true

    init {
        // パケット送信スレッドを起動
        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch {
            while (isRunning) {
                processQueue()
                delay(10) // 10msごとにチェック
            }
        }
    }

    // パケットをキューに追加（送信を遅延させる）
    fun queuePacket(packet: Packet<*>, delayMillis: Long) {
        val sendTime = System.currentTimeMillis() + delayMillis
        packetQueue.add(packet to sendTime)
    }

    // キューからパケットを送信
    private fun processQueue() {
        val currentTime = System.currentTimeMillis()
        while (packetQueue.isNotEmpty()) {
            val (packet, sendTime) = packetQueue.peek()
            if (currentTime >= sendTime) {
                mc.netHandler.addToSendQueue(packet)
                packetQueue.poll()
            } else {
                break // 送信時刻に達していないパケットがある場合は終了
            }
        }
    }

    fun start() {
        isRunning = true
    }

    // ハンドラの終了
    fun stop() {
        isRunning = false
    }
}