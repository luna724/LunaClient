package luna724.iloveichika.binsniper.configs.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.Config
import io.github.notenoughupdates.moulconfig.annotations.Category
import luna724.iloveichika.binsniper.BinSniper

class BinSniperConfig : Config() {
    override fun getTitle(): String {
        return ("§dLunaClient §9Bin§6Sniper §r" + BinSniper.VERSION) + " by §dluna724§r, config menu by §channibal2§r, §5Moulberry §rand §5nea89";
    }

    override fun saveNow() {
        BinSniper.configManager.save()
    }

    @Expose
    @Category(name = "AH-Snipe", desc = "Snipe Auction house BINs")
    var ahSniper: AHSniper = AHSniper()
}