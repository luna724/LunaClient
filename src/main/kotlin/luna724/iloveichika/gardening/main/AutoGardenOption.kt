package luna724.iloveichika.gardening.main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken

class AutoGardenOption {
    companion object {
        val gson: Gson = Gson()
        val mapType = object : TypeToken<Map<String, Any>>() {}.type

        /**
         * AutoGardenが有効か否か
         */
        var isEnabled: Boolean = true;

        /**
         * AutoGardenのセッション設定
         * nullの場合空として扱うために、AutoGardenGetOpt.kt:getSessionOption 関数で取得することを推奨
         */
        var sessionOption: Map<String, sessionOpt>? = null

        /**
         * 空っぽのsessionOption
         * 比較時に使う
         */
        val emptySessionOption: Map<String, sessionOpt> = gson.fromJson("{}", mapType)

    }
}
