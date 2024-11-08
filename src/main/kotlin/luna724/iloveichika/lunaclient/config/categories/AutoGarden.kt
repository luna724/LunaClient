package luna724.iloveichika.lunaclient.config.categories

import com.google.gson.annotations.Expose
import io.github.moulberry.moulconfig.annotations.ConfigEditorBoolean
import io.github.moulberry.moulconfig.annotations.ConfigOption

class AutoGarden {
    @Expose
    @ConfigOption(name = "First Toggle", desc = "Enable this toggle to activate a feature.")
    @ConfigEditorBoolean
    var testButton = false
}