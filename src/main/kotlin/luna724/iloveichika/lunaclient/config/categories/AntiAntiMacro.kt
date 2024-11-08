package luna724.iloveichika.lunaclient.config.categories

import com.google.gson.annotations.Expose
import io.github.moulberry.moulconfig.annotations.Accordion
import io.github.moulberry.moulconfig.annotations.ConfigEditorBoolean
import io.github.moulberry.moulconfig.annotations.ConfigOption

class AntiAntiMacro {
    @ConfigOption(name = "Chat Category", desc = "A sub-category for chat features")
    @Accordion
    @Expose
    var chat: ChatSubCategory = ChatSubCategory()

    class ChatSubCategory {
        @Expose
        @ConfigOption(name = "Duplicate Chat", desc = "Duplicate all chat messages.")
        @ConfigEditorBoolean
        var duplicateChatMessage: Boolean = false
    }
}