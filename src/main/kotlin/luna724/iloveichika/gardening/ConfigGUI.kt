package luna724.iloveichika.gardening

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import scala.sys.Prop
import java.io.File

object Config : Vigilant (
    File(Gardening.configDirectory, "config.toml"),
    "§dLunaClient §f/ §2Auto-Garden"
) {
    @Property(
        type = PropertyType.SWITCH, name = "Main toggle",
        description = "Anti-AntiMacro Main toggle",
        category = "Anti-AntiMacro", subcategory = "main"
    )
    var antiAntiMacroMainToggle = true

    @Property(
        type = PropertyType.SWITCH, name = "Auto-Command when AAM Triggered",
        description = "Automatic send command when Anti-AntiMacro Triggered",
        category = "Anti-AntiMacro", subcategory = "main"
    )
    var antiAntiMacroCommand = false

    @Property(
        type= PropertyType.TEXT, name = "Commands",
        description = "Must be without \"/\"",
        category = "Anti-AntiMacro", subcategory = "main"
    )
    var antiAntiMacroCommands = "lunaclient:simply_limboed"
}
