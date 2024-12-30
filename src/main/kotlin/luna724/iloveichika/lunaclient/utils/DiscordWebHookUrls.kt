package luna724.iloveichika.lunaclient.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import luna724.iloveichika.lunaclient.LunaClient.Companion.LocalServerIP
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import java.net.HttpURLConnection
import java.net.URL

object DiscordWebHookUrls {
    private fun setJsonObj(content: String, username: String?, avatarUrl: String? = null): String? {
        val objectMapper = ObjectMapper()
        val objectNode: ObjectNode = objectMapper.createObjectNode()
        objectNode.put("content", content)
        objectNode.put("secureHttp", 0)
        objectNode.put("username", username ?: "LunaClient")
        objectNode.put("avatar_url", avatarUrl ?: "")
        return try {
            objectMapper.writeValueAsString(objectNode)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            throw e
        }
    }

    /**
     * 指定したテキストを Discord Webhook に送信する
     *
     * forceUserName と forceAvatarUrl を指定しない場合、デフォルトが使用される
     */
    fun sendTextDataToDiscord(
        text: String, forceUserName: String? = null, forceAvatarUrl: String? = null
    ) {
        try {
            Thread({
                val username = forceUserName ?: ("[AutoErrorReporter](" + mc.session?.username + ")")
                val userImage = forceAvatarUrl ?: "https://avatars.githubusercontent.com/u/111692896?v=4"
                val body = setJsonObj(text, username, userImage)!!
                val connection = URL("$LocalServerIP/send_secure_webhook").openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                connection.doOutput = true
                connection.outputStream.use { os ->
                    os.write(body.toByteArray(Charsets.UTF_8))
                }
                val responseCode = connection.responseCode
                val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()
            }).start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}