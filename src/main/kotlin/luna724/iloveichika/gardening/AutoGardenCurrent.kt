package luna724.iloveichika.gardening;

import luna724.iloveichika.gardening.util.SessionOpt

class AutoGardenCurrent {
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

        /**
         * Anti-AntiMacro Invalid Teleport のステータス
         * adminConfig.antiAntiMacroStopAtInvalidTeleport が True である必要がある
         */
        var enableInvalidTeleportDetector: Boolean = true
    }
}
