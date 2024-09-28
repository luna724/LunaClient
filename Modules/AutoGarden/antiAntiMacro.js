// ブロックで一定時間以上止められている際に、AutoMoveを自動停止する

import { stopAutoGarden } from "./autoGarden";
import { compareXYZ } from "./compare";
import { autoGardenSetting } from "./gui";
import { antiAntiMacroStatus, getStatus } from "./Option";

let previousXYZ = [0, -1, 0];  // 以前のXYZを保存する変数
let previousTime = 0;    // 前回処理が実行された時間を保存する
const delay = 3000;      // 3秒間同じ XYZ を無視する

function triggered() {
  if (!getStatus()) { return; }
  if (!antiAntiMacroStatus()) { return; }
  // AutoGardenが有効なら、AutoGardenを停止する
  stopAutoGarden("§cStopped AutoGarden by Anti-AntiMacro");
  
  if (autoGardenSetting.autoDisconnectWhenTriggered) {
    ChatLib.command(autoGardenSetting.autoDisconnectWhenTriggeredCommand.replace(/^\/+|\/+$/g, ""), true);
  }

  previousXYZ = [-1, -1, -1];
}

export function handleXYZ(XYZ) { 
  const currentTime = Date.now();  // 現在のタイムスタンプを取得
  // 前回の処理から delay 時間が経過していれば triggered() を呼び出す
  // 以前の XYZ と同じで、かつ delay を上回った場合
  if (compareXYZ(previousXYZ, XYZ)) {
    if (currentTime - previousTime >= delay) {
      // console.log("Same XYZ. Triggered");
      triggered();
    }
    return;
  }

  // 新しい XYZ を処理
  previousXYZ = XYZ;
  previousTime = currentTime;  // 処理した時間を記録
}


