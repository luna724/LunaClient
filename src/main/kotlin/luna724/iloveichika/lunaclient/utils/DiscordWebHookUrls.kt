package luna724.iloveichika.lunaclient.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import net.minecraft.client.Minecraft
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object DiscordWebHookUrls {
    const val errorReportingServer: String =
        "https://discord.com/api/webhooks/1319273640466055189/HV0W-eGRxsVUgOdKHL0q5VvZnnny8VN6iYH2edrg-OMEYbft__ruE0phLQYO5Z63p0S9"
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
        text: String?, forceUserName: String? = null, forceAvatarUrl: String? = null
    ) {
        if (text == null) { return }
        try {
            Thread({
                val url = errorReportingServer
                if (!url.startsWith("https://")) return@Thread
                val mc = Minecraft.getMinecraft() ?: return@Thread
                val username = forceUserName ?: ("[AutoErrorReporter](" + mc.session?.username + ")")
                val userImage = forceAvatarUrl ?: "https://avatars.githubusercontent.com/u/111692896?v=4"
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