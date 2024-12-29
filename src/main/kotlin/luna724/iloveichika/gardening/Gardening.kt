package luna724.iloveichika.gardening

import luna724.iloveichika.gardening.pest.PestCounter
import luna724.iloveichika.gardening.pest.PestInfo
import luna724.iloveichika.gardening.util.AutoGardenSession
import luna724.iloveichika.gardening.util.PlayerPosUtil
import luna724.iloveichika.gardening.util.SessionOptions
import luna724.iloveichika.gardening.util.WarpGardenAlias
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File
import java.nio.file.Path

class Gardening {
    fun preInit(event: FMLPreInitializationEvent) {
        metadata = event.modMetadata
        val directory =  File(File(event.modConfigurationDirectory, "lunaclient"), "autogarden")
        directory.mkdirs()
        configDirectory = directory
        currentSessionOptionsPath = File(configDirectory, "auto_garden.current.json").toPath()

        val tomlConfigManager = TomlConfigManager(File(File(File(event.modConfigurationDirectory, "lunaclient"), "autogarden"), "adminConfig.toml"))
        adminConfig = tomlConfigManager.config
        val pestCounter = PestCounter()
        pestInfo = pestCounter.config

        val gardenAlias = WarpGardenAlias()
        val commandLCG = Command()
        val devCommand = DevCommand()
        sessionOptionUtil = SessionOptions()
        playerPosUtil = PlayerPosUtil()

        ClientCommandHandler.instance.registerCommand(devCommand)
        ClientCommandHandler.instance.registerCommand(gardenAlias)
        ClientCommandHandler.instance.registerCommand(commandLCG)
    }

    fun onInit() {
        antiAntiMacro = AntiAntiMacro()
        val pestCounter = PestCounter()
        autoGarden = AutoGarden()
        session = AutoGardenSession()

        MinecraftForge.EVENT_BUS.register(autoGarden)
        MinecraftForge.EVENT_BUS.register(antiAntiMacro)
        MinecraftForge.EVENT_BUS.register(pestCounter)

        sessionOptionUtil.safetyModule()
    }

    companion object {
        lateinit var configDirectory: File
        lateinit var metadata: ModMetadata
        lateinit var currentSessionOptionsPath: Path
        lateinit var adminConfig: AdminConfig
        lateinit var pestInfo: PestInfo
        lateinit var autoGarden: AutoGarden
        lateinit var session: AutoGardenSession
        lateinit var playerPosUtil: PlayerPosUtil
        lateinit var antiAntiMacro: AntiAntiMacro
        lateinit var sessionOptionUtil: SessionOptions

        const val HEADER: String = "§6[§2Auto-Garden§6]§f: "
    }
}