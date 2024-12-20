package luna724.iloveichika.lunaclient.config.categories.autogarden

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class PestTrackerConfig {
    @ConfigOption(name = "WARN at nPests spawned.", desc = "\"meow\" announce when pest counts reached\n" +
            "0 to Disable feature")
    @Expose
    @ConfigEditorSlider(minValue = 0f, maxValue = 8f, minStep = 1f)
    var warnAtPestSpawned: Float = 5f

    @ConfigOption(name = "STOP at nPests spawned.", desc = "stop AutoGarden when pest counts reached.\n" +
                                                            "0 to Disable feature")
    @Expose
    @ConfigEditorSlider(minValue = 0f, maxValue = 8f, minStep = 1f)
    var stopAtPestSpawned: Float = 5f

    @ConfigOption(name = "Announce Window", desc = "Announce \"Auto-Garden Stopped by. PestCount\" in Other GUI")
    @Expose
    @ConfigEditorBoolean
    var stopAnnounceInOtherGUI: Boolean = false
}