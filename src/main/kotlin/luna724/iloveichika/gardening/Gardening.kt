package luna724.iloveichika.gardening

import luna724.iloveichika.automove.gdCommand
import luna724.iloveichika.gardening.main.loadSessionOpt
import luna724.iloveichika.gardening.main.tickAutoGarden
import luna724.iloveichika.gardening.pest.PestCounter
import luna724.iloveichika.gardening.pest.PestInfo
import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.LunaClient.Companion.currentGui
import net.minecraft.client.Minecraft
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.io.File
import java.nio.file.Path

@Mod(
    modid = "autogarden",
    name = "LunaClient / Gardening",
    version = "0",
    useMetadata = true,
    clientSideOnly = true
)
class Gardening {
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        metadata = event.modMetadata
        val directory =  File(File(event.modConfigurationDirectory, "lunaclient"), event.modMetadata.modId)
        directory.mkdirs()
        configDirectory = directory
        config = Config
        sessionPth = File(configDirectory, "auto_garden.session.json").toPath()

        val tomlConfigManager = TomlConfigManager(File(File(File(event.modConfigurationDirectory, "lunaclient"), event.modMetadata.modId), "adminConfig.toml"))
        adminConfig = tomlConfigManager.config
        val pestCounter = PestCounter()
        pestInfo = pestCounter.config

        val gdCommand = gdCommand()
        val commandLCG = Command()

        ClientCommandHandler.instance.registerCommand(gdCommand)
        ClientCommandHandler.instance.registerCommand(commandLCG)
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        val aamInstances: AntiAntiMacro = AntiAntiMacro()
        val pestCounter = PestCounter()


        MinecraftForge.EVENT_BUS.register(aamInstances)
        MinecraftForge.EVENT_BUS.register(pestCounter)

        loadSessionOpt()
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        tickAutoGarden()
    }

    companion object {
        val mc: Minecraft = LunaClient.mc
        lateinit var configDirectory: File
        lateinit var config: Config
        lateinit var metadata: ModMetadata
        lateinit var sessionPth: Path
        lateinit var adminConfig: AdminConfig
        lateinit var pestInfo: PestInfo

        const val HEADER: String = "§6[§2Auto-Garden§6]§f: "

        fun openGUI() {
            currentGui = config.gui()
        }
    }
}