package luna724.iloveichika.automove

import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

class AutoMoveMod {
    private var autoMove: AutoMove? = null // autoMove の定義を修正

    fun preInit(event: FMLPreInitializationEvent) {
        // 設定ファイルの場所を指定してSettingsを初期化
        val configFile = File(event.modConfigurationDirectory, "luna724_automove.cfg")
        autoMoveSettings = Settings(configFile)

        // AutoMoveを初期化
        val rotationManager: RotationManager = RotationManager()
        autoMoveInstance = AutoMove(autoMoveSettings, rotationManager) // autoMove の初期化

        // イベントバスに登録してTickイベントを監視
        MinecraftForge.EVENT_BUS.register(autoMoveInstance)
        MinecraftForge.EVENT_BUS.register(rotationManager)
    }

    fun onInit(event: FMLInitializationEvent?) {
        // クライアントサイドのコマンドを登録
        ClientCommandHandler.instance.registerCommand(CommandAutoMove(autoMoveInstance))
        ClientCommandHandler.instance.registerCommand(gdCommand())
    }

    companion object {
        const val MODID: String = "automove"
        const val NAME: String = "LC-AutoMove"
        const val VERSION: String = "2.4"

        lateinit var autoMoveInstance: AutoMove
        lateinit var autoMoveSettings: Settings
    }
}