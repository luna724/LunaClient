## この機能は統合により全廃止されました。
## この機能が必要な場合、[AutoMove v2.3](https://github.com/luna724/LC-AutoMove) を使用してください

## Request lists
全てのパターンは以下の開始と終了パターンを持っています。 <br>
開始: `${LunaAPI.Identifier}` ("§7[§dLunaAPI§7]:§7§f ") <br>
終了: `${(AutoMove.)endsIdentifier}` ("§7**LC-Identifier") <br>

出力例: `message = LunaAPI.Identifier + "return pattern" + endsIdentifier;`

| Request | Info | Return Pattern |
| --- | --- | --- |
| isEnabled? | AutoMoveが有効か否かを返す | `isEnabled? @{isEnabled}` |
| hasClick? | hoverClickの値を返す | `hasClick? @{hasClick}` |
| movingKey? | 正則化されたsetdirectionの値を返す | `movingKey? @{movingKey}` |
| VERSION? | AutoMoveのバージョンを返す | `VERSION? @{VERSION}` |
| POST | 引数1の値を受け取る。現在意味はない | `STATUS? @200` |
| rotateYaw | 引数1にYaw、引数2にtakenTickを受け取り、プレイヤーのYawを変更する。代替えコマンドととして `/automove setyaw <yaw>` が存在する。| `Changing Yaw to {yaw}. Taking {takenTick}ticks` |
| sendStatus | 引数1にステータス、以降の引数に文字列を受け取り、LC-AutoMoveのメゾットでメッセージを送信する。| `sent by. LunaClient - {status.upper()}` |
| その他 | 上記に含まれない値が渡された際に実行される | `AutoMove Options: ${isEnabled}&${hasClick}&${movingKey}` |
