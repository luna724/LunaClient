# `/lcg setxyz <direction> (ignoreY) (changePitch)`
<p> トリガー座標を設定するコマンド </p>
<p> 現在地と現在の向いている向きをもとに設定する </p>
<p> `(ignoreY)` `(changePitch)` は必須ではなく、その通りの引数名としてコマンドに追加することで有効化可能 </p>

| 最終更新 |
| --- |
| 2.0 |

## 代用関数 `/lcg-dev setxyz <x> <y> <z> <yaw> <pitch> <direction> <changePitch> <key>`
`<changePitch>` には true または false を代入


### 機能修正案
- `Yaw`, `Pitch` をコマンドで固定可能にする