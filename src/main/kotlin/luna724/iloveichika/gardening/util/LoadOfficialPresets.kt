package luna724.iloveichika.gardening.util

import kotlinx.coroutines.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import luna724.iloveichika.gardening.Gardening.Companion.sessionOptionUtil
import luna724.iloveichika.lunaclient.LunaClient.Companion.LocalServerIP
import luna724.iloveichika.lunaclient.sendChatError
import luna724.iloveichika.lunaclient.sentErrorOccurred
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LoadOfficialPresets {
    companion object {
        private val availableLocalPresetPart1: List<String> = listOf(
            "nw", "nether_wart", "wheat", "potato", "carrot", "wheat", "part1", "1"
        )
        private val availableLocalPresetPart2: List<String> = listOf(
            "cactus_old", "shitcacti", "part2", "2"
        )
        private val availableLocalPresetPart3: List<String> = listOf(
            "cactus", "part3", "3"
        )
        private val availableLocalPresetAIO: List<String> = listOf(
            "aio", "all"
        )
    }

    fun validateLocalKey(key: String): Boolean {
        val availableLocalKeys: List<String> = listOf(availableLocalPresetPart3, availableLocalPresetPart2, availableLocalPresetPart1,
            availableLocalPresetAIO).flatten()
        return availableLocalKeys.contains(key.lowercase())
    }

    fun loadLocal(key: String): Boolean? {
        if (!validateLocalKey(key)) {
            return null
        }

        // リソースからファイルを読み込み
        var resourcePath = "official_presets/"
        if (availableLocalPresetPart1.contains(key.lowercase())) {
            resourcePath += "part1_v2.json"
        }
        else if (availableLocalPresetPart2.contains(key.lowercase())) {
            resourcePath += "part2_v2.json"
        }
        else if (availableLocalPresetPart3.contains(key.lowercase())) {
            resourcePath += "part3_v2.json"
        }
        else if (availableLocalPresetAIO.contains(key.lowercase())) {
            resourcePath += "all_in_one.json"
        }

        val inputStream: InputStream = this.javaClass.classLoader.getResourceAsStream(resourcePath) ?: return null
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = reader.readText()
        val mapper = object : TypeToken<LinkedHashMap<String, SessionOpt>>() {}.type
        val data: LinkedHashMap<String, SessionOpt> = Gson().fromJson(content, mapper)

        reader.close()
        inputStream.close()

        sessionOptionUtil.saveSessionOption(data)
        return true
    }


    private fun loadJsonRequest(body: String): String {
        val connection = URL("$LocalServerIP/gardening_LoadCloudPreset").openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            // リクエストボディを書き込み
            connection.outputStream.use { os ->
                os.write(body.toByteArray(Charsets.UTF_8))
            }

            // レスポンスを取得
            connection.inputStream.use { input ->
                input.bufferedReader().use { it.readText() }
            }
        } finally {
            connection.disconnect()
        }
    }

    fun loadCloud(key: String): Boolean? {
        try {
            var returnValue: Boolean? = null
            Thread {
                val response = loadJsonRequest("{\"key\":\"$key\"}")
                if (response.isEmpty() || response == "") {
                    returnValue = false
                    return@Thread
                }
                if (response.startsWith("FATAL ERROR")) {
                    sendChatError(response)
                    returnValue= false
                    return@Thread
                }

                val cleanedJson = response
                    .replace("\\n", "")     // 不要な改行エスケープを削除
                    .replace("\\\"", "\"")  // \" を " に置換
                    .removePrefix("\"")     // 先頭の " を取り除く
                    .removeSuffix("\"")
                println("Response: $cleanedJson")
                val mapper = object : TypeToken<LinkedHashMap<String, SessionOpt>>() {}.type
                val data: LinkedHashMap<String, SessionOpt> = Gson().fromJson(cleanedJson, mapper)
                sessionOptionUtil.saveSessionOption(data)
                returnValue = true
            }
            return returnValue
        }
        catch (e: Throwable) {
            e.printStackTrace()
            return null
        }
    }
}