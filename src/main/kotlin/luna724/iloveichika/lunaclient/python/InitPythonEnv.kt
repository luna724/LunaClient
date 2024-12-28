package luna724.iloveichika.lunaclient.python

import luna724.iloveichika.lunaclient.LunaClient.Companion.configDirectory
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.FileSystems.getFileSystem
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class InitPythonEnv {
    private fun executeShell(command: String): String {
        return try {
            val process = ProcessBuilder(
                *command.split(" ").toTypedArray())
                .redirectErrorStream(true)
                .start()
            process.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error executing command: ${e.message}"
        }
    }

    private fun installRequirements() {
        executeShell("python -m pip install $requirements")
    }

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

    companion object {
        const val requirements: String = "fastapi uvicorn gunicorn pip==24.3.1"

    }

    init {
        installRequirements()
        copyAllPythonFiles()
    }
}