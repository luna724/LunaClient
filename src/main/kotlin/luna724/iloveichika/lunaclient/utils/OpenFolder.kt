package luna724.iloveichika.lunaclient.utils

import java.awt.Desktop
import java.io.File
import java.io.IOException

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