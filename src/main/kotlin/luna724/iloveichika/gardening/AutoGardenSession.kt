package luna724.iloveichika.gardening;

import luna724.iloveichika.gardening.util.SessionOpt

/**
 * インスタンス化すると予期せぬ動作が起こる
 */
class AutoGardenSession {
    companion object {
        /**
         * AutoGardenが有効か否か
         */
        var isEnabled: Boolean = false;

        /**
         * AutoGardenのセッション設定
         * nullの場合空として扱うために、AutoGardenGetOpt.kt:getSessionOption 関数で取得することを推奨
         */
        var sessionOption: LinkedHashMap<String, SessionOpt>? = null
    }

    fun isEnable(): Boolean {
        return isEnabled
    }
    fun setEnable(enable: Boolean) {
        isEnabled = enable
    }
    fun sessionOption(): LinkedHashMap<String, SessionOpt>? {
        return sessionOption
    }
    fun setSessionOption(new: LinkedHashMap<String, SessionOpt>) {
        sessionOption = new
    }
}