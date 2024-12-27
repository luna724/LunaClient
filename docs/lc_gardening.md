## Auto Garden
LunaClient v1.1.4 / Auto Garden は以下のMODを要求します。
- [LC-AutoMove](https://github.com/luna724/LC-AutoMove/releases): v2.2以上

- `/lcgardening` でゲーム内ヘルプを表示


### コマンド一覧
全てのコマンドは `/lc_gardening` で開始します <br>
[v2の場合、Wikiにて全仕様を確認できます](https://luna724.github.io/repo/lunaclient)

- `/lcg gui` <br>
プリセットに依存しない固定変数の設定があるメニュー <br>
`/lcgui` でも開くことができる。

- `/lcg <start/stop>` / `/lcg toggle` <br>
Auto Gardenのスタート/ストップ <br>

- `/lcg setxyz <trigger> (ignoreY)` <br>
現在地の X,Y,Z,Yaw,Pitch を現在のプリセットの最後に保存する <br>
<trigger>は、その位置に到着した 500ms 後に移動する方向で、
`/automove setdirection` の第二引数に入れられるので
ここに代入される値は `/automove setdirection` が受け入れる値である必要がある。
なお、到着処理の際に Yaw, Pitch は無視される <br>
`(ignoreY)` オプションで追加し、Y座標のチェックを無視する.
方法は第二引数に ignorey と入れるだけ

- `/lcg removexyz <targetKey>` <br>
<targetKe>を現在のプリセットから削除する <br>

- `/lcg listxyz` <br>
現在のプリセットに登録されているキーとその値をリストアップする <br>

- `/lcg currentxyz` <br>
現在地XYZにてトリガーするキーを表示する <br>
 
- `/lcg getxyz <targetKey>` <br>
<targetKey>の X,Y,Z,Yaw,Pitch を取得し表示する <br>

- `/lcg preset <save/load/delete/rename/current/new> <targetPreset> (presetAfter)` <br>
  - `save <targetPreset>` <br>
  現在の設定でプリセットのセーブを行う。
  同盟のプリセットがある場合は上書きされるので注意

  - `load <targetPreset>` <br>  
  プリセットの読み込みを行う。
  ここで読み込まれたプリセットの値をもとに他すべての動作が行われる

  - `delete <targetPreset>` <br>
  プリセットの削除を行う。

  - `rename <targetPreset> <presetAfter>` <br>
  プリセットの名前変更を行う。
  変更先の名前がある場合はエラーを返す。

  - `current` <br>
  現在選択されているプリセット名を表示する。

  - `new <targetPreset>` <br>
  全ての値が初期状態の新しいプリセットを作る
  同名のプリセットがある場合はエラーを返す

- `/lcg timer <time:s> (aterCommand=lcg stop)` <br>
<time:s> にて指定した秒数経過後、(afterCommand) のコマンドを実行する <br>
(afterCommand) のコマンドは、クライアントサイドであり、`/`を含まないものである必要がある。 <br>

- `/lcg collect <Name> <SaveName>` <br>
<Name>にて指定したプリセットを取得する。 <br>
対象ファイルは `LunaClient/loader`にある `.lcg.presets.json`ファイルでなければならない。 <br>


### 依存ファイル (引継ぎなど向け)
#### - `/autogarden.json`
現在読み込まれているプリセットの XYZManager情報を保持している <br>
取得メゾット: `getConfig()`

#### - `../LunaClientAutoGarden/config.toml`
AutoGardenの主要設定を保持する <br>
取得メゾット: gui.js:autoGardenSettings

#### - `/auto_garden.session.json`
プログラムのステータスを保持する <br>
この設定はtickイベントにより読み込まれているので、軽量であることが重要 <br>
取得メゾット: `getSessionConfig()`

#### - `/presets/auto_garden.presets.json`
プリセットの保存ファイル <br>
内部構造は以下のとおり

```json
{
  "presetName": {"autogarden.json in this preset"},
  "presetName2": {"autogarden.json in this preset"},
  ..
}
```