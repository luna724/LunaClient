package luna724.iloveichika.lunaclient.utils

import java.util.Base64
import kotlin.random.Random

fun generateUniqueBase64Key(existingKeys: Set<String>): String {
    fun generateBase64Key(byteSize: Int = 4): String {
        val bytes = ByteArray(byteSize)
        Random.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    var newKey = generateBase64Key()

    // 重複チェック
    var i: Int = 0;
    while (existingKeys.contains(newKey)) {
        i++ // 10回以上重複したらバイト数を変換し、重複しづらくする
        if (i > 10) {
            newKey = generateBase64Key(i-6)
        }
        else {
            newKey = generateBase64Key() // できる限り 4バイトで生成する
        }
    }

    return newKey
}
