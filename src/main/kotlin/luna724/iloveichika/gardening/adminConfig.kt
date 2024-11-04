package luna724.iloveichika.gardening

import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import java.io.File

data class AdminConfig(
    val antiAntiMacroTriggerDelay: Long = 3000 // ms
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