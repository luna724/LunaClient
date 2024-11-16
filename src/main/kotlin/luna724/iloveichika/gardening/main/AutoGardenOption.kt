package luna724.iloveichika.gardening.main;

class AutoGardenOption {
    companion object {
        /**
         * AutoGardenが有効か否か
         */
        var isEnabled: Boolean = true;

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
