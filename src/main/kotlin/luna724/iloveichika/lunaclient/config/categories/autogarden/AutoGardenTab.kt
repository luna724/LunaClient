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


}