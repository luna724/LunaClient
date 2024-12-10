package luna724.iloveichika.lunaclient.python

import luna724.iloveichika.lunaclient.LunaClient
import scala.actors.threadpool.Executors
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.security.MessageDigest

class PythonAPI {
    companion object {
        val pythonDir: File = File(LunaClient.configDirectory, "python")
        val python: File = File(LunaClient.configDirectory, "python\\venv\\Scripts\\python.exe")
        val internalScript: File = File(LunaClient.configDirectory, "Scripts")

        init {
            // venvがないなら作成、python.exeがないなら再生成
            pythonDir.mkdirs()

            File(pythonDir, "userScript").mkdirs()
            internalScript.mkdirs()
            makeAll()

            val venv = File(pythonDir, "venv")
            if (!(venv.exists() && venv.isDirectory)) {
                // 作成
                val processBuilder = ProcessBuilder(
                    "python -m venv venv"
                )
                processBuilder.directory(pythonDir)
                processBuilder.redirectErrorStream(true)
                val process = processBuilder.start()
                val exitCode = process.waitFor()
                val output = process.inputStream.bufferedReader().readText()

                if (exitCode != 0) {
                    LunaClient.logger.error(output)
                    throw RuntimeException("Exception in Creating python venv (LunaClient.py), see logs for more Information")
                }
                LunaClient.logger.info("venv created.")

                // Install requirements
                val requirementsInstaller = ProcessBuilder(
                    python.absolutePath + " requirements.py"
                )
                requirementsInstaller.directory(internalScript)
                requirementsInstaller.redirectErrorStream(true)
                val process2 = requirementsInstaller.start()
                val exitCode2 = process2.waitFor()
                val output2 = process2.inputStream.bufferedReader().readText()
                if (exitCode2 != 0) {
                    LunaClient.logger.error(output2)
                    throw RuntimeException("Exception in installing requirements (LunaClient.py), see logs for more information")
                }
                LunaClient.logger.info("requirements installed.")
            }
            else {
                LunaClient.logger.warn("venv already exists.")
            }
        }

        private fun makeAll() {
            // リソース内にあるpythonファイルをすべて internalScript にコピー
            // ディレクトリ構造を維持し、インポートを可能にする
            if (!internalScript.exists()) {
                internalScript.mkdirs()
            }

            // クラスローダーからリソースのリストを取得
            val resourceBase = "/python"
            val classLoader = object {}.javaClass.classLoader

            classLoader.getResourceAsStream(resourceBase.removePrefix("/"))?.use { stream ->
                val tempDir = Files.createTempDirectory("python_resources").toFile()
                tempDir.deleteOnExit()

                val resourceDir = File(tempDir, "python")
                resourceDir.mkdirs()

                // ストリームの内容を一時ディレクトリに展開
                Files.copy(stream, resourceDir.toPath(), StandardCopyOption.REPLACE_EXISTING)

                // 再帰的にコピー
                resourceDir.walkTopDown().forEach { file ->
                    if (file.isFile && file.extension == "py") {
                        val relativePath = file.relativeTo(resourceDir).path
                        val targetFile = File(internalScript, relativePath)
                        targetFile.parentFile.mkdirs()
                        Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    }
                }
            }

            LunaClient.logger.info("All Python scripts copied to ${internalScript.absolutePath}")
        }
    }

    private val executor = Executors.newFixedThreadPool(4)
    private fun processPython(script: String, critical: Boolean = false): String {
        // 一時ファイルに保存
        val bytes = script.toByteArray()
        val md = MessageDigest.getInstance("SHA-256") // ファイル名を SHA256から生成
        val digest = md.digest(bytes)
        val scriptName = digest.joinToString("") { "%02x".format(it) } // ハッシュを16進文字列に変換
        val scriptFile = File(pythonDir, "userScript\\$scriptName.py")
        scriptFile.writeText(script) // ファイルに書き込み

        // Pythonを実行
        val processBuilder = ProcessBuilder(python.absolutePath, "-U", scriptFile.absolutePath)
        processBuilder.redirectErrorStream(true)
        processBuilder.directory(pythonDir)

        val process = processBuilder.start()
        val exitCode = process.waitFor()
        val output = process.inputStream.bufferedReader().readText()

        if (exitCode != 0) {
            LunaClient.logger.error("Exception in running python: $scriptName.\nError: $output")
            if (critical) {
                throw RuntimeException("Exception in running python file (LunaClient). see logs for more info")
            }
            return output
        }
        LunaClient.logger.info("run python: $scriptName success.")
        return output
    }

    fun run(script: String, critical: Boolean = false, threading: Boolean = false): String {
        var result: String = ""
        if (threading) {
            executor.submit {
                result = processPython(script, critical)
            }
        }
        else {
            result = processPython(script, critical)
        }
        return result
    }


}