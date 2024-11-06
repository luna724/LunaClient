package luna724.iloveichika

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import net.minecraft.client.Minecraft
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection


object discordWebHookUrls {
    const val errorReportingServer: String =
        "https://discordapp.com/api/webhooks/1298218474736189491/WVMpU_uH8gIP95ymZ_z6CFb4gd6pWltA8pYbIbuSlblsnZxjHlIhRNlpiWJezZdc9L6t"

    private fun setJsonObj(content: String?, username: String?, avatarUrl: String? = null): String? {
        val objectMapper: ObjectMapper = ObjectMapper()
        val objectNode: ObjectNode = objectMapper.createObjectNode()
        objectNode.put("content", content)
        if (!username.isNullOrEmpty()) {
            objectNode.put("username", username)
        }
        if (!avatarUrl.isNullOrEmpty()) {
            objectNode.put("avatar_url", avatarUrl)
        }
        return try {
            objectMapper.writeValueAsString(objectNode)
        } catch (e: JsonProcessingException) {
            null
        }
    }

    fun sendTextDataToDiscord(
        text: String?, url: String?
    ) {
        if (text == null || url == null) { return }
        try {
            Thread({
                val mc = Minecraft.getMinecraft() ?: return@Thread
                val username = "[AutoErrorReporter]("+mc.session?.username+")"
                var userImage: String? = null

                if (username == "[AutoErrorReporter](Mizuki_25ji)") {
                    userImage = "https://avatars.githubusercontent.com/u/111692896?v=4"
                }

                val jsonObj = setJsonObj(text, username, userImage) ?: return@Thread
                val endpoint: URL = URL(url)
                val connection: HttpsURLConnection = endpoint.openConnection() as HttpsURLConnection
                connection.addRequestProperty("Content-Type", "application/JSON; charset=utf-8")
                connection.addRequestProperty("User-Agent", "DiscordBot")
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Length", jsonObj.length.toString())
                val outputStream: OutputStream = connection.outputStream
                outputStream.write(jsonObj.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()
                val responseCode: Int = connection.getResponseCode()
                if (responseCode != 200 && responseCode != 204) {
                    println("error:$responseCode")
                }
                connection.disconnect()
            }).start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}