package luna724.iloveichika.automove

import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

class AutoMoveMod {
    fun preInit(event: FMLPreInitializationEvent) {
        // 設定ファイルの場所を指定してSettingsを初期化
        val configFile = File(event.modConfigurationDirectory, "luna724_automove.cfg")
        autoMoveSettings = AutoMoveSettings(configFile)

        // AutoMoveを初期化
        rotationManager = RotationManager()
        autoMoveInstance = AutoMove(autoMoveSettings, rotationManager) // autoMove の初期化

        // イベントバスに登録してTickイベントを監視
        MinecraftForge.EVENT_BUS.register(autoMoveInstance)
        MinecraftForge.EVENT_BUS.register(rotationManager)
    }

    fun onInit() {
        ClientCommandHandler.instance.registerCommand(CommandAutoMove(autoMoveInstance))
    }

    companion object {
        lateinit var autoMoveInstance: AutoMove
        lateinit var autoMoveSettings: AutoMoveSettings
        lateinit var rotationManager: RotationManager
    }
}