package luna724.iloveichika.gardening.pest

import luna724.iloveichika.gardening.Gardening.Companion.pestInfo
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.LunaClient.Companion.scoreboardUtil
import luna724.iloveichika.lunaclient.LunaClient.Companion.tabListUtil
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent

data class PestInfo(
    var pestCount: Int = 0, var repellentType: String = "", var bonusRemain: String = ""
)

class PestCounter {
    var config: PestInfo = PestInfo()

    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        val test = mc.thePlayer?.rotationYaw ?: return

        val currentTabList = tabListUtil.getTabList()
        val currentScoreboard = scoreboardUtil.getScoreboardValues()
        val (match, currentAreaText) = tabListUtil.findObjRegex(currentTabList, "Area:.*")
        if (!match) {
            sentErrorOccurred("Current Area Cannot found (AreaRaw: $currentAreaText)"); return
        }
        if (!EnumChatFormatting.getTextWithoutFormattingCodes(currentAreaText).contains("Garden")) {
            sentErrorOccurred("Current Area aren't Garden: ($currentAreaText)")
            return
        }

        // Garden にいるなら
        var (locateFound, locateString) = scoreboardUtil.findObjRegex(currentScoreboard, "⏣.*")
        if (!locateFound) {
            sentErrorOccurred("Exception in Parsing current area p2"); return
        }
        locateString = EnumChatFormatting.getTextWithoutFormattingCodes(locateString).replace(
            " ⏣ The Garde🌠n", ""
        )
        locateString = if (" ൠ " in locateString) {
            locateString.replace("ൠ x", "");
        }
        else {
            "0"
        }
        val pestCount: Int = locateString.toIntOrNull() ?: -1
        if (pestCount == -1) {
            sentErrorOccurred("Failed Parsing PestCount (result: $locateString)")
            return
        }
        pestInfo.pestCount = pestCount

        // Repellent Type
        var (repellentFound, repellentString) = tabListUtil.findObjRegex(currentTabList, "^ Repellent: (.*)")
        if (!repellentFound) {
            sentErrorOccurred("Repellent Status Couldn't Found")
            return
        }
        repellentString = EnumChatFormatting.getTextWithoutFormattingCodes(repellentString).replace(
            "Repellent: ", ""
        )
        val repellentStatus: Boolean = repellentString.contains("(")
        val repellentType: String = if (!repellentStatus) {
            "INACTIVE"
        }
        else if (repellentString.contains("MAX")) {
            "MAX"
        }
        else {
            "REGULAR"
        }
        pestInfo.repellentType = repellentType

        // BONUS
        var (bonusFound, bonusString) = tabListUtil.findObjRegex(currentTabList, "^ Bonus: (.*")
        if (!bonusFound) {
            sentErrorOccurred("Bonus Status Couldn't Found")
            return
        }
        bonusString = bonusString.replace(
            "Bonus: ", ""
        )
        if (EnumChatFormatting.getTextWithoutFormattingCodes(bonusString).contains("INACTIVE")) {
            pestInfo.bonusRemain = "INACTIVE"
            return
        }
        bonusString = bonusString.split("§b")[1]
        val bonusRemain = EnumChatFormatting.getTextWithoutFormattingCodes(bonusString)
        pestInfo.bonusRemain = bonusRemain

        println(pestInfo.pestCount.toString() + " and " + pestInfo.repellentType + " and " + pestInfo.bonusRemain)
    }
}