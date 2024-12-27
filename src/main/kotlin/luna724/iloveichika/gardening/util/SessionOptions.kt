package luna724.iloveichika.gardening.util

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import luna724.iloveichika.gardening.Gardening.Companion.configDirectory
import luna724.iloveichika.gardening.Gardening.Companion.currentSessionOptionsPath
import luna724.iloveichika.lunaclient.sentErrorOccurred
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class SessionOpt(
    val coordinates: List<Double> = listOf(0.0, 0.0, 0.0), val orientation: List<Double> = listOf(0.0, 0.0), val direction: String = "reset", val changePitch: Boolean = false
)    // coordinates: [X,Y,Z], orientation: [Yaw, Pitch], direction: anyDirection (literalString), changePitch: changePitch

class SessionOptions {
    companion object {
        private val currentSessionOptionFile: File = File(currentSessionOptionsPath.toUri())

        // バックアップ先
        private val sessionOptionBackupDirectory: File = File(
            configDirectory, "backup_session"
        )

        // 使用可能な direction
        val availableDirectionChar: String = "rlfb"
        val availableDirectionTrigger: List<String> = listOf("reset", "spawn")
    }

    init { // 必ずエラーになるものを事前に作成
        sessionOptionBackupDirectory.mkdirs()
        checkSessionOptionExists()
    }

    /**
     * SessionOption が存在するかどうかの確認を行う
     *
     * 存在しない場合、空の内容で再生成する
     */
    private fun checkSessionOptionExists() {
        if (!currentSessionOptionFile.exists()) {
            // ファイルがない場合のみ空の内容で保存
            currentSessionOptionFile.writeText("{}")
        }
    }

    /**
     * 実際に開いてみてキャッチ可能なエラーをすべて試す
     *
     * また、致命的なものはそのままスローする
     */
    private fun trySessionOptionIO() {
        try {
            val sessionOptionString = currentSessionOptionFile.readText()
            val sessionOptionHashMap: LinkedHashMap<String, SessionOpt> = Json.decodeFromString(sessionOptionString)
        }
        catch (e: IOException) {
            // IOExceptionはスロー
            sentErrorOccurred("IOException occurred while reading sessionOption")
            println("IOException in LCG.trySessionOptionIO !!!")
            throw e
        }
        catch (e: SerializationException) {
            // SerializationException はユーザーに警告を発生させ、ファイルをリセットする
            sentErrorOccurred("SerializationException occurred while reading sessionOption. do you change SessionJson?", report = false)
            sentErrorOccurred("SessionJson wiped. (backup in config/lunaclient/autogarden/backup_session)", report = false)
            val now = LocalDateTime.now()
            val nowTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS"))
            val fileName: String = "session_backup.$nowTime.json"
            val dst = File(sessionOptionBackupDirectory, fileName)

            try {
                if (!currentSessionOptionFile.renameTo(dst)) {
                    sentErrorOccurred("Failed to move sessionOption file")
                    throw e
                }
            }
            catch (e: SecurityException) {
                sentErrorOccurred("SecurityException occurred while moving sessionOption file", report = false)
                throw e
            }
            catch (e: IOException) {
                sentErrorOccurred("IOException occurred while moving sessionOption file", report = false)
                throw e
            }
            // ファイルをリセット
            if (currentSessionOptionFile.exists()) {
                currentSessionOptionFile.delete()
            }
            checkSessionOptionExists()
        }
        // 何も起きなかったら問題なし

        // MissingFieldException
        // UnknownKeyException の二つは起こりえないものとして無視
    }

    /**
     * SessionOpt IO 時に必ず呼び出すべき関数
     *
     * IOエラーの可能性を事前に解消する
     *
     * 外部クラスから呼び出すことはあまり推奨しない (Gardening.kt:onInit にて利用させるためにprivateではない)
     */
     fun safetyModule() {
        // ファイルがないなら作成
        checkSessionOptionExists()

        // ファイルの中身が正しくない場合、チャットに送信しバックアップを作成後クリーンアップする
        trySessionOptionIO()
    }

    /**
     * SessionOpt の連番を用いり保存する
     */
    @OptIn(ExperimentalSerializationApi::class) // prettyPrintIndent の有効化
    fun saveSessionOption(
        sessionOption: LinkedHashMap<String, SessionOpt>
    ) {
        val jsonFormatter = Json {
            prettyPrint = true
            prettyPrintIndent = "  "
        }

        // オブジェクトをjson形式に変換し、保存
        val jsonString = jsonFormatter.encodeToString(sessionOption)
        currentSessionOptionFile.writeText(jsonString)
    }

    /**
     * SessionOpt の連番を返す
     *
     * 実質的にはJsonを読み込んだものをそのまま返すため、多用される関数
     */
    fun loadSessionOption(): LinkedHashMap<String, SessionOpt> {
        safetyModule()

        val jsonRawString = currentSessionOptionFile.readText()
        return Json.decodeFromString<LinkedHashMap<String, SessionOpt>>(jsonRawString)
    }

    /**
     * 単一の SessionOpt とキーを受け取り、連番に追加する
     *
     * この動作は現在のファイルを即座に変更する
     *
     * @return: 追加に成功したかどうか
     */
    fun addSessionOpt(
        key: String, sessionOpt: SessionOpt, overwrite: Boolean = true
    ): Boolean {
        val currentSessionOption = loadSessionOption()

        if (!overwrite && currentSessionOption.containsKey(key)) return false
        currentSessionOption[key] = sessionOpt
        saveSessionOption(currentSessionOption)
        return true
    }

    /**
     * 単一の SessionOpt をキーから消す
     *
     * この動作は現在のファイルを即座に変更する
     *
     * @return: キーが存在したかどうか
     */
    fun removeSessionOpt(
        key: String
    ): Boolean {
        val currentSessionOption = loadSessionOption()
        if (!currentSessionOption.containsKey(key)) return false
        currentSessionOption.remove(key)
        saveSessionOption(currentSessionOption)
        return true
    }

    /**
     * SessionOpt の direction が正しいかどうかを確かめる
     *
     * 利用可能な値は object にて定義される
     *
     * @return: 正しい場合は渡した値、正しくない場合は Null を返す。
     *
     * そのため、val direction = isDirectionValid(args[1]) ?: "reset" のような利用を想定している
     */
    fun isDirectionValid(
        direction: String
    ): String? {

    }
}

