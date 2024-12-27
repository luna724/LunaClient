package luna724.iloveichika.lunaclient.config.categories.autogarden

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class AutoGardenTab {
    @ConfigOption(name= "movementCooldown", desc= "cooldown for swap movement (ms)")
    @Expose
    @ConfigEditorSlider(
        minValue = 50f,
        maxValue = 10000f,
        minStep = 10f)
    var movementCooldown: Float = 500f

    @ConfigOption(name= "X-Z Tolerance", desc="tolerance for Auto-Garden X-Z coordinate check")
    @Expose
    @ConfigEditorSlider(
        minValue = 0f,
        maxValue = 2f,
        minStep = 0.05f)
    var xzTolerance: Float = 0.35f

    @ConfigOption(name= "Y Tolerance", desc="tolerance for Auto-Garden Y coordinate check")
    @Expose
    @ConfigEditorSlider(
        minValue = 0f,
        maxValue = 5f,
        minStep = 0.1f
    )
    var yTolerance: Float = 0.1f
}