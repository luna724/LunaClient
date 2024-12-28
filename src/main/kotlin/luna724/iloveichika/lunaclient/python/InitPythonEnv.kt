package luna724.iloveichika.lunaclient.python

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luna724.iloveichika.lunaclient.LunaClient.Companion.configDirectory
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.FileSystems.getFileSystem
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class InitPythonEnv {
    /**
     * shell コマンドを別スレッドで実行
     */
    private fun executeShellAsync(command: String, targetDir: File? = null) {
        Thread {
            println("Thread started for $command")
            executeShell(command, targetDir)
        }.start()
    }

    /**
     * shell コマンドを実行
     */
    private fun executeShell(command: String, targetDir: File? = null) {
         try {
                val process = ProcessBuilder(
                    *command.split(" ").toTypedArray()
                )
                    .apply {
                        if (targetDir != null) directory(targetDir)
                        redirectErrorStream(true)
                    }
                    .start()
                process.waitFor()
                process.inputStream.bufferedReader().useLines {
                    lines -> println(lines)
                }
                // process.inputStream.bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                e.printStackTrace()
             // "Error executing command: ${e.message}"
            }
    }

    /**
     * requirements をインストール
     */
    private fun installRequirements() {
        executeShell("python -m pip install $requirements")
    }


    /**
     * すべてのファイルを移動
     */
    private fun copyAllPythonFiles() {
        val pyResourcePath = this::class.java.getResource("/python")
            ?: throw IllegalArgumentException("Python resource path not found")
        val targetDir = File(configDirectory, "python")
        targetDir.mkdirs()
        val uri = pyResourcePath.toURI()
        val env = mutableMapOf<String, String>()

        if (uri.scheme != "jar") {
            throw IllegalArgumentException("Python resource path must be a jar")
        }
        val fileSystem = FileSystems.newFileSystem(uri, env)
        val path = fileSystem.getPath("/python")

        Files.walk(path).forEach { source ->
            val target = targetDir.resolve(path.relativize(source).toString()).toPath()
            if (!Files.isDirectory(source)) {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }

    /**
     * サーバーの起動
     */
    private fun launchServer() {
        executeShellAsync("python -m uvicorn server:app --port 8888", File(configDirectory, "python"))
    }

    companion object {
        const val requirements: String = "fastapi uvicorn gunicorn pip==24.3.1"

    }

    init {
        println("Initializing Python Server for LunaClient..")
        installRequirements()
        copyAllPythonFiles()
        launchServer()
        println("Python Server for LunaClient initialized.")
    }
}