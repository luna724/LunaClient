package luna724.iloveichika.lunaclient.utils

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.lunaclient.sentErrorOccurred
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScoreObjective
import net.minecraft.scoreboard.Scoreboard
import java.util.regex.Matcher
import java.util.regex.Pattern

class ScoreboardUtil {
    private fun getCurrentScoreboard(): Pair<String, List<String>> {
        val scoreboard: Scoreboard? = mc.theWorld?.scoreboard
        val obj: ScoreObjective? = scoreboard?.getObjectiveInDisplaySlot(1)
        obj ?: run {
            // scoreboardが存在しないなら
            // sentErrorOccurred("Scoreboard not found. it will maybe cause any errors", report = false)
            return Pair("", listOf())
        }

        val title: String = obj.displayName
        val scoreLists = scoreboard.getSortedScores(obj)
        val scoreboardValues = scoreLists.map { value ->
            value.playerName ?: ""
        }
        return Pair(title, scoreboardValues)
    }

    fun getScoreboardValues(): List<String> {
        val (_, values) = getCurrentScoreboard()
        return values
    }

    fun getScoreboardTitle(): String {
        val (title, _) = getCurrentScoreboard()
        return title
    }

    /** obj に find が含まれているかをチェックする
     * 一つでもマッチすれば true を返す
     */
    fun findObject(obj: List<String>? = null, find: String): Boolean {
        val tabList = obj ?: getScoreboardValues()
        for (tab in tabList) {
            if (tab.equals(find, ignoreCase = true)) return true
        }
        return false
    }

    /** obj に matches が含まれているか Regex でチェックする
     * マッチした最初の要素と true のペアを返す
     */
    fun findObjRegex(obj: List<String>? = null, matches: String): Pair<Boolean, String> {
        val tabList = obj ?: getScoreboardValues()
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