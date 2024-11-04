package luna724.iloveichika.gardening

import com.examplemod.config.PersistentData
import luna724.iloveichika.gardening.main.tickAutoGarden
import luna724.iloveichika.lunaclient.LunaClient
import luna724.iloveichika.lunaclient.LunaClient.Companion.currentGui
import luna724.iloveichika.lunaclient.config.Config
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModMetadata
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
        Gardening.metadata = event.modMetadata
        val directory =  File(File(event.modConfigurationDirectory, "lunaclient"), event.modMetadata.modId)
        directory.mkdirs()
        Gardening.configDirectory = directory
        Gardening.config = luna724.iloveichika.gardening.Config
        Gardening.sessionPth = File(Gardening.configDirectory, "auto_garden.session.json").toPath()
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        tickAutoGarden()
    }

    companion object {
        val mc: Minecraft = LunaClient.mc
        lateinit var configDirectory: File
        lateinit var config: luna724.iloveichika.gardening.Config
        lateinit var metadata: ModMetadata
        lateinit var sessionPth: Path
    }
}