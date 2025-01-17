package luna724.iloveichika.binsniper

import luna724.iloveichika.binsniper.ah.BinSnipeLogic
import luna724.iloveichika.binsniper.configs.ConfigManager
import luna724.iloveichika.lunaclient.LunaClient
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

class BinSniper {
    fun preInit(event: FMLPreInitializationEvent) {
        configDirectory = File(LunaClient.configDirectory, "binsniper")
        configDirectory.mkdirs()
    }

    fun onInit() {
        // Register key bindings
        binSniperKey = KeyBinding("Snipe", 25, "key.categories.lunaclient.binsniper")
        ClientRegistry.registerKeyBinding(binSniperKey)
        ClientCommandHandler.instance.registerCommand(RegisterCommands())

        MinecraftForge.EVENT_BUS.register(BinSnipeLogic())
    }

    companion object {
        lateinit var configDirectory: File
        lateinit var binSniperKey: KeyBinding
        lateinit var configManager: ConfigManager

        const val HEADER: String = "§f[§9Bin§6Sniper§f]§r: "
        const val VERSION: String = "2.0"
    }
}