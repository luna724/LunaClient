package luna724.iloveichika.lunaclient.config.categories;

import luna724.iloveichika.lunaclient.LunaClient;
import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.Config
import io.github.notenoughupdates.moulconfig.annotations.Category

class ModConfig : Config() {
    override fun getTitle(): String {
        return ("§dLunaClient " + LunaClient.VERSION) + " by §dluna724§r, config menu by §channibal2§r, §5Moulberry §rand §5nea89";
    }

    override fun saveNow() {
        LunaClient.configManager.save()
    }

    @Expose
    @Category(name = "AutoGarden", desc = "AutoGarden's Config")
    var autoGardenCategory: AutoGarden = AutoGarden()

//    @Expose
//    @Category(name = "AutoMove",)
}