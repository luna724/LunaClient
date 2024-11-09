package luna724.iloveichika.lunaclient.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class AutoGarden {
    @ConfigOption(name = "First Toggle", desc = "Enable this toggle to activate a feature.")
    @Expose
    @ConfigEditorBoolean
    var testButton = false
}