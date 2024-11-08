package luna724.iloveichika.lunaclient.utils

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import net.minecraft.client.network.NetHandlerPlayClient
import java.util.regex.Matcher
import java.util.regex.Pattern

class TabListUtil {
    private fun getCurrentTabList(): List<String>  {
        val connection:NetHandlerPlayClient = mc.netHandler
        val playerInfoList = connection.playerInfoMap

        val tabListText = playerInfoList.map { playerInfo ->
            playerInfo.displayName?.formattedText ?: playerInfo.gameProfile.name
        }

        return tabListText
    }

    fun getTabList(): List<String> {
        return getCurrentTabList()
    }

    /** obj に find が含まれているかをチェックする
     * 一つでもマッチすれば true を返す
     */
    fun findObject(obj: List<String>? = null, find: String): Boolean {
        val tabList = obj ?: getTabList()
        for (tab in tabList) {
            if (tab.equals(find, ignoreCase = true)) return true
        }
        return false
    }

    /** obj に matches が含まれているか Regex でチェックする
     * マッチした最初の要素と true のペアを返す
     */
    fun findObjRegex(obj: List<String>? = null, matches: String): Pair<Boolean, String> {
        val tabList = obj ?: getTabList()
        val pattern: Pattern = Pattern.compile(matches)
        for (tab in tabList) {
            val matcher: Matcher = pattern.matcher(tab)
            if (matcher.find()) {
                return Pair(true, matcher.group())
            }
        }
        return Pair(false, "")
    }
}