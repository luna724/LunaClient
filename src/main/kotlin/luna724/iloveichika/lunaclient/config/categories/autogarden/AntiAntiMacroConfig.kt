package luna724.iloveichika.lunaclient.config.categories.autogarden

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class AntiAntiMacroConfig {
    @ConfigOption(name = "Main toggle", desc = "a features for Macro bypass\n")
    @Expose
    @ConfigEditorBoolean
    var antiAntiMacroMainToggle: Boolean = false

    @ConfigOption(name = "XYZ Check", desc = "Track your movements, Trigger when stop over 3s")
    @Expose
    @ConfigEditorBoolean
    var xyzTracker: Boolean = false

    @ConfigOption(name = "Teleportation Check", desc = "Track S08PacketPlayerPosLook")
    @Expose
    @ConfigEditorBoolean
    var s08PacketPlayerTracker: Boolean = false

    @ConfigOption(name = "Player Check", desc = "Detect player around you\nignore AntiBot valid")
    @Expose
    @ConfigEditorBoolean
    var playerTracker: Boolean = false
}