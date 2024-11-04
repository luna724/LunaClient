package luna724.iloveichika.gardening

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import scala.sys.Prop
import java.io.File

object Config : Vigilant (
    File(Gardening.configDirectory, "config.toml"),
    "§dLunaClient §f/ §2Auto-Garden"
){
    @Property(
        type = PropertyType.SWITCH, name = "Main toggle",
        description = "Anti-AntiMacro Main toggle",
        category = "Anti-AntiMacro", subcategory = "main"
    )
    var antiAntiMacroMainToggle = true

}