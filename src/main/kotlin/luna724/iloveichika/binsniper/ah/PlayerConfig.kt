package luna724.iloveichika.binsniper.ah

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import luna724.iloveichika.binsniper.BinSniper.Companion.configDirectory
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import java.io.File

@Serializable
data class AHSniperConfig(
    var MaxCost: Int = -1, // 最大購入価格
    var MinCost: Int = -1, // 最低購入価格
    var Delay: Int = 500, // MS
    var Reconnect: Boolean = true,
    var AmountLimit: Int = 0,
    var Timeout: Int = 10000, // MS
    var uuidBlackList: Boolean = false, // 重複した UUID を購入しない
    var NullSafe: Boolean = true // NPE が起こった際に処理を続行する
)


class Config {
    /**
     * プレイヤー毎に設定される AH Sniper の設定を保存、処理する
     */
    companion object {
        private val baseConfigFile: File = File(
            configDirectory, "player_config"
        )
    }

    init {
        baseConfigFile.mkdir()
    }

    /**
     * デフォルト設定を保存する
     */
    private fun makeDefaultConfig(path: File) {
        val defaultConfig = AHSniperConfig()
        val jsonFormatter = Json {
            prettyPrint = true
        }
        path.writeText(
            jsonFormatter.encodeToString(AHSniperConfig.serializer(), defaultConfig)
        )
    }

    /**
     * ファイルが存在しない場合などの例外処理
     * 主に IOException を防止する
     */
    private fun safetyModule(uuid: String? = null) {
        baseConfigFile.mkdir()
        uuid ?: return

        val playerConfigFile = File(baseConfigFile, "$uuid.json")
        if (!playerConfigFile.exists()) {
            playerConfigFile.createNewFile()
            makeDefaultConfig(playerConfigFile)
        }
    }

    /* 設定 */
    private var isEnable: Boolean = false

    /**
     * 現在のプレイヤーのUUIDを取得する
     */
    private fun getPlayerUUID(): String {
        return mc.session.profile.id.toString()
    }

    /**
     * プレイヤー毎の設定を読み込む
     */
    fun loadPlayerConfig(): AHSniperConfig {
        val playerUUID = getPlayerUUID()
        safetyModule(playerUUID)

        val playerConfigFile = File(baseConfigFile, "$playerUUID.json")
        val jsonRawString = playerConfigFile.readText()
        return Json.decodeFromString(
            jsonRawString
        )
    }

    /**
     * プレイヤー毎の設定を保存する
     */
    fun savePlayerConfig(config: AHSniperConfig) {
        val playerUUID = getPlayerUUID()
        safetyModule(playerUUID)

        val playerConfigFile = File(baseConfigFile, "$playerUUID.json")
        val jsonFormatter = Json {
            prettyPrint = true
        }
        playerConfigFile.writeText(
            jsonFormatter.encodeToString(AHSniperConfig.serializer(), config)
        )
    }


}