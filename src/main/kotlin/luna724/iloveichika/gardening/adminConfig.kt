package luna724.iloveichika.gardening

import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import java.io.File

data class AdminConfig(
    /**プレイヤーがこの期間動かなかったら停止する*/
    val antiAntiMacroTriggerDelay: Long = 3000 ,// ms
    /**100msの誤差を追加しその期間経過後に XYZ Triggered Event を実行する*/
    val antiAntiMacroMethodCooldown: Long = 250 ,// Threshold: ~100ms
    /**Anti-AntiMacro内でエラーがあった場合でも、AutoGardenの動作を続ける*/
    val antiAntiMacroKeepException: Boolean = true , // if true, when catch exceptions won't stop AutoGarden. Only catches Anti-AntiMacro's Exception
    /** it will be enabled here
     * antiAntiMacro...
     * StopAtInvalidTeleport
     *      * StopAtDetectPlayerOnNearYou
     *      * StopAtServerClosingDetected
     *      * StopAtAnyGuestJoined
     *      * StopAtnSecondsNotTriggered
     *      * StopAtGroundBlockChanged
     * IgnoreAirFloor
     * IgnoreExceptFloorMS
     *
     * StopAtRandomly
     */
    val antiAntiMacroUseBetaOption: Boolean = false ,

    /**NOT RECOMMENDED!*/
    val antiAntiMacroStopAtInvalidTeleport: Boolean = false ,
    /**Range: 10x10 Square*/
    val antiAntiMacroStopAtDetectPlayerOnNearYou: Boolean = true ,
    /**Server Closing event detected from Chat&Scoreboard*/
    val antiAntiMacroStopAtServerClosingDetected: Boolean = false ,
    /**Detect from scoreboard*/
    val antiAntiMacroStopAtAnyGuestJoined: Boolean = true ,
    /**SystemTime Changes may break this feature*/
    val antiAntiMacroStopAtnSecondsNotTriggered: Boolean = true ,
    val antiAntiMacroStopAtnSecondsNotTriggeredMS: Long = 1000*60*5 ,// 5 minutes
    /**プレイヤーから ~ ~-1 ~ のブロックを用いる*/
    val antiAntiMacroStopAtFloorChanged: Boolean = false ,
    /**床の変更を許容する時間*/
    val antiAntiMacroAcceptableFloorChangingTime: Long = 1000*5 , // 5seconds
    /**空気なら無視する*/
    val antiAntiMacroFloorDetecterIgnoreAir: Boolean = false ,
    /**各動作ごとに一定確率で停止する物を有効化する*/
    val antiAntiMacroStopAtRandomly: Boolean = false ,
    )

class TomlConfigManager(private val configFile: File) {
    var config: AdminConfig = AdminConfig()
    private val tomlWriter = TomlWriter()

    init {
        loadConfig()
    }

    fun loadConfig() {
        if (configFile.exists()) {
            config = Toml().read(configFile).to(AdminConfig::class.java)
        } else {
            saveConfig()  // 初回はデフォルト設定を保存
        }
    }

    fun saveConfig() {
        tomlWriter.write(config, configFile)
    }
}


/**
 * 使用例
 *
 * val tomlConfigManager = TomlConfigManager(File("config/adminConfig.toml"))
 * val config = tomlConfigManager.config
 * config.someSetting = "newTomlValue" // 設定の変更
 * tomlConfigManager.saveConfig()       // 保存
 *
 */