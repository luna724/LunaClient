package luna724.iloveichika.gardening

import luna724.iloveichika.automove.gdCommand
import luna724.iloveichika.gardening.util.loadSessionOpt
import luna724.iloveichika.gardening.pest.PestCounter
import luna724.iloveichika.gardening.pest.PestInfo
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.io.File
import java.nio.file.Path

class Gardening {
    fun preInit(event: FMLPreInitializationEvent) {
        metadata = event.modMetadata
        val directory =  File(File(event.modConfigurationDirectory, "lunaclient"), "autogarden")
        directory.mkdirs()
        configDirectory = directory
        currentSettingJsonPath = File(configDirectory, "auto_garden.current.json").toPath()

        val tomlConfigManager = TomlConfigManager(File(File(File(event.modConfigurationDirectory, "lunaclient"), "autogarden"), "adminConfig.toml"))
        adminConfig = tomlConfigManager.config
        val pestCounter = PestCounter()
        pestInfo = pestCounter.config

        val gdCommand = gdCommand()
        val commandLCG = Command()
        val devCommand = DevCommand()

        ClientCommandHandler.instance.registerCommand(devCommand)
        ClientCommandHandler.instance.registerCommand(gdCommand)
        ClientCommandHandler.instance.registerCommand(commandLCG)
    }

    fun onInit(event: FMLInitializationEvent) {
        val aamInstances = AntiAntiMacro()
        val pestCounter = PestCounter()
        autoGarden = AutoGarden()

        MinecraftForge.EVENT_BUS.register(autoGarden)
        MinecraftForge.EVENT_BUS.register(aamInstances)
        MinecraftForge.EVENT_BUS.register(pestCounter)

        loadSessionOpt()
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
//        tickAutoGarden()
    }

    companion object {
        lateinit var configDirectory: File
        lateinit var metadata: ModMetadata
        lateinit var currentSettingJsonPath: Path
        lateinit var adminConfig: AdminConfig
        lateinit var pestInfo: PestInfo
        lateinit var autoGarden: AutoGarden

        const val HEADER: String = "§6[§2Auto-Garden§6]§f: "

    }
}