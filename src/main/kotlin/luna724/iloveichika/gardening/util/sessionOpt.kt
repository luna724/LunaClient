package luna724.iloveichika.gardening.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import luna724.iloveichika.gardening.Gardening.Companion.currentSettingJsonPath
import java.io.File

@Serializable
data class SessionOpt(
    val coordinates: List<Double>, val orientation: List<Double>, val direction: String, val changePitch: Boolean = false
)    // coordinates: [X,Y,Z], orientation: [Yaw, Pitch], direction: anyDirection (literalString), changePitch: changePitch

/**
 * sessionOptを含むJSONファイルを読み込む
 * パス: .../auto_garden.current.json
 */
fun loadSessionOpt(): LinkedHashMap<String, SessionOpt> {
    if (!File(currentSettingJsonPath.toUri()).exists()) {
        File(currentSettingJsonPath.toUri()).writeText("{}")
        return LinkedHashMap()
    }
    val jsonString = File(currentSettingJsonPath.toUri()).readText()
    return Json.decodeFromString(jsonString)
}

/**
 * sessionOptを含むJSONを書き込む(保存)
 * パス: .../auto_garden.current.json
 */
@OptIn(ExperimentalSerializationApi::class) // カスタムインデントを有効化
fun saveSessionOpt(sessionOption: LinkedHashMap<String, SessionOpt>) {
    // インデント付きでフォーマットする設定を有効化
    val jsonFormatter = Json {
        prettyPrint = true
        prettyPrintIndent = "  " // インデントを2スペースに変更
    }

    // オブジェクトをJSON形式にシリアル化
    val jsonString = jsonFormatter.encodeToString(sessionOption)
    File(currentSettingJsonPath.toUri()).writeText(jsonString)
}

/**
 * sessionOptに値を追加し、即座に保存する
 */
fun addSessionOpt(key: String, sessionOpt: SessionOpt) {
    val s = loadSessionOpt()
    s[key] = sessionOpt
    saveSessionOpt(s)
}

/**
 * sessionOptの値をキーを用いり消し、即座に上書きする
 */
fun removeSessionOpt(key: String) {
    val s = loadSessionOpt()
    if (!s.containsKey(key)) return
    s.remove(key)
    saveSessionOpt(s)
}