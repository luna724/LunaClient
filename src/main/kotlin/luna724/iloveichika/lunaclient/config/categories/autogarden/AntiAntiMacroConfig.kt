package luna724.iloveichika.lunaclient.config.categories.autogarden

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class AntiAntiMacroConfig {
    @ConfigOption(name = "Main toggle", desc = "a features for Macro bypass\n")
    @Expose
    @ConfigEditorBoolean
    var antiAntiMacroMainToggle: Boolean = true

    @ConfigOption(name = "AAM-Triggered command", desc = "commands to execute when AntiAntiMacro triggered\nempty to run nothing. slash needed (no slash to send chat message!!!)")
    @Expose
    @ConfigEditorText
    var antiAntiMacroCommand: String = "/lc_file run autogarden/antiantimacro_triggered_commands.txt"

    @ConfigOption(name = "XYZ Check", desc = "Track your movements, and stop AutoGarden when you stopped.")
    @Expose
    @ConfigEditorBoolean
    var xyzTracker: Boolean = true

    @ConfigOption(name = "[XYZ Check] acceptable time (ms)", desc = "ignore same XYZ position for this time")
    @Expose
    @ConfigEditorSlider(
        minValue = 0f,
        maxValue = 10000f,
        minStep = 100f
    )
    var xyzTrackerAcceptableTimeMS: Float = 3000f

    @ConfigOption(name = "Player Check", desc = "Detect player around you\nignore AntiBot valid")
    @Expose
    @ConfigEditorBoolean
    var playerTracker: Boolean = false
}