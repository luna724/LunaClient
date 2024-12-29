package luna724.iloveichika.lunaclient.utils

import luna724.iloveichika.lunaclient.LunaClient.Companion.configDirectory
import java.io.File

class InitializeLunaClientDependFiles {
    companion object {
        val makeDirs: List<String> = listOf(
            "autogarden"
        )
        val makeFiles: Map<String, String?> = mapOf( // キーをファイル、値をその中身とし、中身が null の場合空にする
            "autogarden/antiantimacro_triggered_commands.txt" to "# https://luna724.github.io/repo/lunaclient/docs/lc_commands/lc_filemanager.html"
        ) // 作成するファイルのリストすべてのパスは config/lunaclient からの相対パス
    }

    init {
        makeFileMain()
    }

    /**
     * 作成
     */
    private fun makeFileMain() {
        // まずはディレクトリを作成する
        for (dir in makeDirs) {
            val dirFile = File(configDirectory, dir)
            if (!dirFile.exists()) {
                dirFile.mkdirs()
            }
        }

        // 次にファイル
        // 中身に相当するものがある場合、それを書き込み保存する
        for (file in makeFiles.keys) {
            val fileFile = File(configDirectory, file)
            if (!fileFile.exists()) {
                fileFile.createNewFile()
                fileFile.writeText(makeFiles.getValue(file) ?: "")
            }
        }
    }
}