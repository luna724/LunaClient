package luna724.iloveichika.lunaclient.utils

import java.awt.Desktop
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

fun openFolder(folderPath: String, critical: Boolean = false) {
    try {
        val folder = File(folderPath)
        // フォルダが存在するか確認
        if (!folder.exists()) {
            if (critical) {
                throw IOException("Folder does not exists: $folderPath")
            }
            else {
                return
            }
        }
        // AWT Desktopクラスを使用してフォルダを開く
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(folder)
                return
            }
        }

        // Desktopがサポートされない場合の代替処理
        val osName = System.getProperty("os.name").lowercase()
        when {
            osName.contains("win") -> Runtime.getRuntime().exec("explorer $folderPath")
            osName.contains("mac") -> Runtime.getRuntime().exec("open $folderPath")
            osName.contains("nix") || osName.contains("nux") -> Runtime.getRuntime().exec("xdg-open $folderPath")
            else -> throw UnsupportedOperationException("Unsupported OS: $osName")
        }
    } catch (ex: Exception) {
        println("フォルダを開く際にエラーが発生しました: ${ex.message}")
        if (critical) throw ex
    }
}

object fileManager {
    /**
     * Jar Resources からリソースをコピー
     */
    fun copyResources(resourcePath: String, targetPath: String) {
        try {
            // リソースファイルを取得
            val resourceAsStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
                ?: throw FileNotFoundException("Resource not found: $resourcePath")

            // ターゲットファイルの親ディレクトリを作成
            val targetFile = File(targetPath)
            val targetDir = targetFile.parentFile
            if (!targetDir.exists() && !targetDir.mkdirs()) {
                throw IOException("Could not create target directory: ${targetDir.absolutePath}")
            }

            // ファイルをコピー
            Files.copy(resourceAsStream, Paths.get(targetPath))

        } catch (e: Exception) {
            println("リソースのコピー中にエラーが発生しました: ${e.message}")
        }
    }
}