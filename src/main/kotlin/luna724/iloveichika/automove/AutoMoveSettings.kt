package luna724.iloveichika.automove

import net.minecraftforge.common.config.Configuration
import java.io.File

class AutoMoveSettings(configFile: File?) {
    // 設定項目
    var autoMoveDirection: Int = 0 // Literal: ["Left", "Right", "Forward", "Backward", "NaN"]
    var autoMoveClickEnable: Boolean = false
    var autoMoveEnabled: Boolean = false
    var autoMoveStopWhenServerSwap: Boolean = false
    var autoMoveInfo: Boolean = false

    // Configurationオブジェクト
    // コンフィグファイルを読み込む
    private val config = Configuration(configFile)

    // コンストラクタで設定を読み込む
    init {
        loadConfig()
    }

    // 設定を読み込む
    fun loadConfig() {
        config.load()

        // 設定項目の読み込み（もしくはデフォルト値の設定）
        autoMoveDirection =
            config.getInt("AutoMoveDirection", CATEGORY_GENERAL, 0, -2147483647, 2147483647, "自動移動の方向")
        autoMoveClickEnable =
            config.getBoolean("AutoMoveClickEnable", CATEGORY_GENERAL, true, "自動移動時にクリックをするかどうか")
        autoMoveEnabled = config.getBoolean("AutoMoveEnabled", CATEGORY_GENERAL, false, "自動移動のすてーたす ")
        autoMoveStopWhenServerSwap = config.getBoolean("AutoMoveStopWhenServerSwap", CATEGORY_GENERAL, true, "")
        autoMoveInfo = config.getBoolean("AutoMoveInfo", CATEGORY_GENERAL, true, "")
    }

    // 設定を保存する
    fun saveConfig() {
        config[CATEGORY_GENERAL, "AutoMoveDirection", autoMoveDirection].set(autoMoveDirection)
        config[CATEGORY_GENERAL, "AutoMoveClickEnable", autoMoveClickEnable].set(autoMoveClickEnable)
        config[CATEGORY_GENERAL, "AutoMoveEnabled", autoMoveEnabled].set(autoMoveEnabled)
        config[CATEGORY_GENERAL, "AutoMoveStopWhenServerSwap", autoMoveStopWhenServerSwap].set(
            autoMoveStopWhenServerSwap
        )
        config[CATEGORY_GENERAL, "AutoMoveInfo", autoMoveInfo].set(autoMoveInfo)

        println("[LC-AutoMove]: Saving config..")
        config.save()
    }

    companion object {
        private const val CATEGORY_GENERAL = "general"
    }
}