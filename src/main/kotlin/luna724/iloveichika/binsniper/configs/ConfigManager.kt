package luna724.iloveichika.binsniper.configs

import io.github.notenoughupdates.moulconfig.gui.GuiScreenElementWrapper
import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor
import io.github.notenoughupdates.moulconfig.processor.BuiltinMoulConfigGuis
import io.github.notenoughupdates.moulconfig.processor.ConfigProcessorDriver
import io.github.notenoughupdates.moulconfig.processor.MoulConfigProcessor
import luna724.iloveichika.binsniper.configs.categories.BinSniperConfig
import luna724.iloveichika.lunaclient.utils.externalobjecttweaker.makeMoulGson
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class ConfigManager {
    companion object {
        val gson = makeMoulGson()
    }

    private var configDirectory = File("config/LunaClient/binsniper")
    private var configFile: File
    var config: BinSniperConfig? = null
    private var lastSaveTime = 0L

    private lateinit var processor: MoulConfigProcessor<BinSniperConfig>
    private val editor by lazy { MoulConfigEditor(processor) }

    init {
        configDirectory.mkdirs()
        configFile = File(configDirectory, "global_config.json")

        if (configFile.isFile) {
            println("Trying to load the config")
            tryReadConfig()
        }

        if (config == null) {
            println("Creating a clean config.")
            config = BinSniperConfig()
        }
        val config = config!!
        processor = MoulConfigProcessor(config)
        BuiltinMoulConfigGuis.addProcessors(processor)
//        UpdateManager.injectConfigProcessor(processor)
        ConfigProcessorDriver(processor).processConfig(config)

        Runtime.getRuntime().addShutdownHook(Thread {
            save()
        })
    }

    fun openConfigGui() {
        screenToOpen = GuiScreenElementWrapper(editor)
    }

    private fun tryReadConfig() {
        try {
            val inputStreamReader = InputStreamReader(FileInputStream(configFile), StandardCharsets.UTF_8)
            val bufferedReader = BufferedReader(inputStreamReader)

            val builder = StringBuilder()
            for (line in bufferedReader.lines()) {
                builder.append(line)
                builder.append("\n")
            }
            config = gson.fromJson(builder.toString(), BinSniperConfig::class.java)

        } catch (e: Exception) {
            throw IllegalArgumentException("Could not load config", e)
        }
    }

    fun save() {
        lastSaveTime = System.currentTimeMillis()
        val config = config ?: throw IllegalArgumentException("Can not save null config.")

        try {
            configDirectory.mkdirs()
            val unit = configDirectory.resolve("config.json.write")
            unit.createNewFile()
            BufferedWriter(OutputStreamWriter(FileOutputStream(unit), StandardCharsets.UTF_8)).use { writer ->
                writer.write(gson.toJson(config))
            }
            // Perform move — which is atomic, unlike writing — after writing is done.
            Files.move(
                unit.toPath(),
                configFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE
            )
        } catch (e: IOException) {
            throw IOException("Could not save config", e)
        }
    }

    private var screenToOpen: GuiScreen? = null

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        Minecraft.getMinecraft().thePlayer ?: return
        if (screenToOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(screenToOpen)
            screenToOpen = null
        }
        if (System.currentTimeMillis() > lastSaveTime + 60_000) {
            save()
        }
    }
}