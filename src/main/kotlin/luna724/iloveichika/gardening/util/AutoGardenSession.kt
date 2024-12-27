package luna724.iloveichika.gardening.util;

/**
 * インスタンス化禁止
 * Gardening.kt companion 内の session のみを使用する
 */
class AutoGardenSession {
    companion object {
        /**
         * AutoGardenが有効か否か
         */
        var isEnabled: Boolean = false;
    }

    fun isEnable(): Boolean {
        return isEnabled
    }

    fun setEnable(enable: Boolean) {
        isEnabled = enable
    }
}