package luna724.iloveichika.lunaclient.utils

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class JsonRequest {
    fun getJsonFromUrl(urlString: String): JSONObject? {
        var result: JSONObject? = null
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    result = JSONObject(response.toString()) // JSONのパース
                }
            } else {
                System.err.println("HTTP error code: ${connection.responseCode}")
            }

        } catch (e: Exception) {
            System.err.println("Error fetching JSON: ${e.message}")
            e.printStackTrace()
        }
        return result
    }

    fun GETJsonFromUrl(urlString: String): JSONObject? {
        var result: JSONObject? = null
        val thread = Thread {
            result = getJsonFromUrl(urlString)
        }
        thread.start()
        thread.join(5000)
        return result
    }
}