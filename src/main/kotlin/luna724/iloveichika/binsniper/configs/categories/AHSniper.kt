package luna724.iloveichika.binsniper.configs.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Accordion
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorButton
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class AHSniper {
    @ConfigOption(name = "Main toggle", desc = "AH Sniper main toggle")
    @Expose
    @Accordion
    var mainToggle: Boolean = true

//    @ConfigOption(name = "Player Blacklist", desc = "/lc_bs blacklist <player> to add, \n/lc_bs blacklist <player> to remove (if player added)")
//    @Expose
//    @ConfigEditorButton("Open File")
}