package luna724.iloveichika.lunaclient.utils

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

fun playSound(soundPath: String, volume: Float = 1f, pitch: Float = 1f) {
    val player = mc.thePlayer ?: return
    val world = mc.theWorld ?: return

    world.playSoundEffect(
        player.posX, player.posY, player.posZ,
        soundPath, volume, pitch)
}

class InfiniSound(
) {
    companion object {
        var soundPath: String = "note.pling"
        var volume: Float = 1f
        var pitch: Float = 1f
    }

    private val scheduler = Executors.newSingleThreadScheduledExecutor()
    private var soundTask: ScheduledFuture<*>? = null

    fun start() {
        soundTask = scheduler.scheduleAtFixedRate({
            playSound(soundPath, volume, pitch)
        }, 0, 500, TimeUnit.MILLISECONDS) // 初回実行間隔[0ms], 実行間隔[50ms]
    }

    fun stop() {
        soundTask?.cancel(true) // タスクの停止
        scheduler.shutdown() // スケジューラの終了
    }

    fun setSound(newSoundPath: String, newVolume: Float = 1f, newPitch: Float = 1f) {
        soundPath = newSoundPath
        volume = newVolume
        pitch = newPitch
    }
}