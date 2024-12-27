package luna724.iloveichika.gardening.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minecraft.util.Session
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class LoadOfficialPresets {
    companion object {
        private val availableCloudKeys: List<String> = listOf(
            "part1", "part2", "part3", "part4"
        )
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

    fun validateCloudKey(key: String): Boolean {

        return true
    }

    fun validateLocalKey(key: String): Boolean {
        val availableLocalKeys: List<String> = listOf(availableLocalPresetPart3, availableLocalPresetPart2, availableLocalPresetPart1,
            availableLocalPresetAIO).flatten()
        return availableLocalKeys.contains(key.lowercase())
    }

    data class Preset(
        val sessions: LinkedHashMap<String, SessionOpt>
    )

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

        saveSessionOpt(data)
        return true
    }
}