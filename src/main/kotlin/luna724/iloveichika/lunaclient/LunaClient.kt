package luna724.iloveichika.lunaclient

import luna724.iloveichika.lunaclient.commands.ExampleCommand
import luna724.iloveichika.lunaclient.config.Config
import com.examplemod.config.PersistentData
import com.sun.security.ntlm.Client
import luna724.iloveichika.lunaclient.commands.SimplyLimbo
import luna724.iloveichika.lunaclient.modules.debug_info.onPlayerLogged
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
import java.io.File

@Mod(
    modid = "lunaclient",
    name = "LunaClient",
    version = "2.0",
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
        config = Config
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ClientCommandHandler.instance.registerCommand(ExampleCommand())
        ClientCommandHandler.instance.registerCommand(SimplyLimbo())

        listOf(
            this
        ).forEach(MinecraftForge.EVENT_BUS::register)
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
        lateinit var config: Config
        lateinit var persistentData: PersistentData

        lateinit var metadata: ModMetadata

        const val errHEADER = "§4[§dLunaClient§4]§c:"
        const val mainHEADER = "§7[§dLunaClient§7]§f:"
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