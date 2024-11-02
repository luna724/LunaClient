package luna724.iloveichika.lunaclient.config

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import luna724.iloveichika.lunaclient.LunaClient
import java.io.File


object Config : Vigilant(
    File(LunaClient.configDirectory, "config.toml"),
    LunaClient.metadata.name
) {
    @Property(
        type = PropertyType.SWITCH, name = "World load notifier",
        description = "Send Client-sided message when world Loaded",
        category = "debug_info", subcategory = "Message"
    )
    var WorldLoadNotifier = false

    @Property(
        type = PropertyType.TEXT, name = "World load notifier text",
        description = "messages when world loaded",
        category = "debug_info", subcategory = "Message"
    )
    var worldLoadNotifierText = ""

    @Property(
        type = PropertyType.SWITCH, name = "Always add header",
        description = "ALL Client-sided messages have LunaClient's header",
        category = "General", subcategory = "QoL"
    )
    var alwaysHeaderOnClientChats = true

    @Property(
        type = PropertyType.SWITCH, name = "Always add header to chat",
        description = "ALL Client/Server-sided (e.g. PartyChat) messages have LunaClient's header",
        category = "General", subcategory = "QoL"
    )
    var alwaysHeaderOnChats = false

    @Property(
        type = PropertyType.SWITCH, name = "Blood room full Notice",
        description = "send message when blood rooms full",
        category = "Dungeons", subcategory = "Chat"
    )
    var bloodRoomFullNotice = false

    init {
        println("Config Directory: ${LunaClient.configDirectory}")
        println("Metadata Name: ${LunaClient.metadata.name}")


//        addDependency("World load notifier text", "World load notifier")
//        addDependency("Always add header to chat", "Always add header")
    }
}