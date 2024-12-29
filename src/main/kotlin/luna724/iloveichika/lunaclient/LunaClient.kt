package luna724.iloveichika.lunaclient

import luna724.iloveichika.automove.AutoMoveMod
import luna724.iloveichika.gardening.Gardening
import luna724.iloveichika.lunaclient.cheating.Blink
import luna724.iloveichika.lunaclient.commands.CommandManager
import luna724.iloveichika.lunaclient.commands.FileManager
import luna724.iloveichika.lunaclient.commands.MainCommand
import luna724.iloveichika.lunaclient.config.ConfigManager
import luna724.iloveichika.lunaclient.config.categories.ModConfig
import luna724.iloveichika.lunaclient.modules.debug_info.onPlayerLogged
import luna724.iloveichika.lunaclient.python.InitPythonEnv
import luna724.iloveichika.lunaclient.utils.*
import luna724.iloveichika.lunaclient.vigilanceConfig.Config
import luna724.iloveichika.lunaclient.vigilanceConfig.PersistentData
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
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
        autoMove = AutoMoveMod()
        autoMove.preInit(event)
        gardening = Gardening()
        gardening.preInit(event)

        tabListUtil = TabListUtil()
        scoreboardUtil = ScoreboardUtil()
        infiniSound = InfiniSound()
        blink = Blink()

        CommandManager()

        // コマンド登録
        ClientCommandHandler.instance.registerCommand(FileManager())
        ClientCommandHandler.instance.registerCommand(MainCommand())
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        listOf(
            this
        ).forEach(MinecraftForge.EVENT_BUS::register)
        configManager = ConfigManager()

        autoMove.onInit()
        gardening.onInit()
        MinecraftForge.EVENT_BUS.register(configManager)
        MinecraftForge.EVENT_BUS.register(gardening)

        // Python ライブラリをインストール
        InitPythonEnv()

        // ファイルを自動生成
        InitializeLunaClientDependFiles()

        // ログイン通知
        DiscordWebHookUrls.sendTextDataToDiscord(
            "${mc.session?.username} joined with LunaClient!", forceUserName = "LunaClient", forceAvatarUrl = ""
        )

        logger.info("LunaClient successfully initialized.")
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
        val logger: Logger = LogManager.getLogger("LunaClient")

        lateinit var gardening: Gardening
        lateinit var autoMove: AutoMoveMod
        lateinit var configDirectory: File
        lateinit var vigilanceConfig: Config
        lateinit var persistentData: PersistentData
        lateinit var configManager: ConfigManager

        lateinit var metadata: ModMetadata

        const val ERRHEADER = "§4[§dLunaClient§4]§c:"
        const val MAINHEADER = "§7[§dLunaClient§7]§f:"
        const val VERSION = "2.0"

        // Utils
        lateinit var tabListUtil: TabListUtil
        lateinit var scoreboardUtil: ScoreboardUtil
        lateinit var blink: Blink

        // Inifinite Sound Instances
        lateinit var infiniSound: InfiniSound

        // config
        val config: ModConfig
            get() = configManager.config ?: error("config is null")
    }

    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        isPlayerJoining = true
        onPlayerLogged()
    }

    @SubscribeEvent
    fun onPlayerLogout(event: PlayerEvent.PlayerLoggedOutEvent) {
        isPlayerJoining = false
    }
}