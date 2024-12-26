package luna724.iloveichika.lunaclient.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Accordion
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption
import luna724.iloveichika.lunaclient.config.categories.autogarden.AntiAntiMacroConfig
import luna724.iloveichika.lunaclient.config.categories.autogarden.AutoGardenTab
import luna724.iloveichika.lunaclient.config.categories.autogarden.PestTrackerConfig

class AutoGarden {
    @ConfigOption(name = "AutoGarden", desc = "AutoGarden features")
    @Expose
    @Accordion
    var autoGarden: AutoGardenTab = AutoGardenTab()

    @ConfigOption(name = "Internal Message", desc = "sent Auto-Garden debug message to chat")
    @Expose
    @ConfigEditorBoolean
    var internalMessage = false

    @ConfigOption(name = "Pestracker", desc = "Pest related features")
    @Expose
    @Accordion
    var pestracker: PestTrackerConfig = PestTrackerConfig()

    @ConfigOption(name = "Anti-AntiMacro", desc = "Hypixel Anti-Macro bypasses")
    @Expose
    @Accordion
    var antiAntiMacroConfig: AntiAntiMacroConfig = AntiAntiMacroConfig()
}