package luna724.iloveichika.lunaclient.cheating

import kotlinx.coroutines.*
import net.minecraft.client.Minecraft
import net.minecraft.network.Packet
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class Blink {
    private fun randomLongInRange(x: Long, y: Long): Long {
        require(x <= y) { "x must be less than or equal to y" }
        val range = y - x + 1
        require(range > 0 && range <= Long.MAX_VALUE) { "Invalid range" }

        val random = Random()
        val randomNumber = random.nextLong()
        // 正の値のみを使用
        val positiveRandomNumber = if (randomNumber < 0) randomNumber + Long.MAX_VALUE else randomNumber
        val result = (positiveRandomNumber % range) + x

        return result
    }

    private var isProcessing = false
    private val mc = Minecraft.getMinecraft()
    private val packetQueue = ConcurrentLinkedQueue<Pair<Packet<*>, Long>>()
    private var isRunning = false

    init {
        // パケット送信スレッドを起動
        Thread {
            while (isRunning) {
                processQueue()
                Thread.sleep(50) // 1ticks
            }
        }.start()
    }

    // パケットをキューに追加（送信を遅延させる）
    fun queuePacket(packet: Packet<*>) {
        val delayMillis: Long = randomLongInRange(100, 750)
        val sendTime = System.currentTimeMillis() + delayMillis
        packetQueue.add(packet to sendTime)
    }

    // キューからパケットを送信
    private fun processQueue() {
        val currentTime = System.currentTimeMillis()
        while (packetQueue.isNotEmpty()) {
            val (packet, sendTime) = packetQueue.peek()
            if (currentTime >= sendTime) {
                isProcessing = true
                mc.netHandler.addToSendQueue(packet)
                isProcessing = false
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

    fun isEnable(): Boolean {
        return isRunning
    }

    fun isProcessing(): Boolean {
        return isProcessing
    }
}