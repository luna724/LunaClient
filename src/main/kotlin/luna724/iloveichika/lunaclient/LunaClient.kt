package luna724.iloveichika.lunaclient

import luna724.iloveichika.lunaclient.commands.CommandManager
import luna724.iloveichika.lunaclient.vigilanceConfig.Config
import luna724.iloveichika.lunaclient.vigilanceConfig.PersistentData
import luna724.iloveichika.lunaclient.config.ConfigManager
import luna724.iloveichika.lunaclient.config.categories.ModConfig
import luna724.iloveichika.lunaclient.modules.debug_info.onPlayerLogged
import luna724.iloveichika.lunaclient.utils.ScoreboardUtil
import luna724.iloveichika.lunaclient.utils.TabListUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.io.File

@Mod(
    modid = "lunaclient",
    name = "LunaClient",
    version = LunaClient.VERSION,
    useMetadata = true,
    clientSideOnly = true
)
class LunaClient {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        metadata = event.modMetadata
        val directory = File(event.modConfigurationDirectory, "lunaclient")
        directory.mkdirs()
        configDirectory = directory
        persistentData = PersistentData.load()
        vigilanceConfig = Config

        tabListUtil = TabListUtil()
        scoreboardUtil = ScoreboardUtil()

        CommandManager()
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        listOf(
            this
        ).forEach(MinecraftForge.EVENT_BUS::register)

        configManager = ConfigManager()
        MinecraftForge.EVENT_BUS.register(configManager)
        sentErrorOccurred("[test]: Mods Initialized!")
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START || currentGui == null) return
        mc.displayGuiScreen(currentGui)
        currentGui = null
    }

    companion object {
        val mc: Minecraft = Minecraft.getMinecraft()
        var currentGui: GuiScreen? = null
        var isPlayerJoining: Boolean = false

        lateinit var configDirectory: File
        lateinit var vigilanceConfig: Config
        lateinit var persistentData: PersistentData
        lateinit var configManager: ConfigManager

        lateinit var metadata: ModMetadata

        const val errHEADER = "§4[§dLunaClient§4]§c:"
        const val mainHEADER = "§7[§dLunaClient§7]§f:"
        const val VERSION = "2.0"

        // Utils
        lateinit var tabListUtil: TabListUtil
        lateinit var scoreboardUtil: ScoreboardUtil

        // config
        val config: ModConfig
            get() = configManager.config ?: error("config is null")
    }

    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        LunaClient.isPlayerJoining = true
        onPlayerLogged()
    }

    @SubscribeEvent
    fun onPlayerLogout(event: PlayerEvent.PlayerLoggedOutEvent) {
        LunaClient.isPlayerJoining = false
    }
}