// ブロックで一定時間以上止められている際に、AutoMoveを自動停止する

import { stopAutoGarden } from "./autoGarden";
import { compareXYZ } from "./compare";
import gui from "./gui";
import { antiAntiMacroStatus, getStatus } from "./Option";
import { getXYZ } from "./XYZ/module";

function triggered() {
  if (!getStatus()) { return; }
  // AutoGardenが有効なら、AutoGardenを停止する
  stopAutoGarden("§cStopped AutoGarden by Anti-AntiMacro");
  
  if (gui.autoDisconnectWhenTriggered) {
    ChatLib.command("skytils:limbo", true);
  }
}

let previousXYZ = [0, -1, 0];  // 以前のXYZを保存する変数
let previousTime = 0;    // 前回処理が実行された時間を保存する
const delay = 3000;      // 3秒間同じ XYZ を無視する

function handleXYZ(XYZ) { 
  const currentTime = Date.now();  // 現在のタイムスタンプを取得
  // 前回の処理から delay 時間が経過していれば triggered() を呼び出す
  // 以前の XYZ と同じで、かつ delay を上回った場合
  if (compareXYZ(previousXYZ, XYZ)) {
    if (currentTime - previousTime >= delay) {
      console.log("Same XYZ. Triggered");
      triggered();
    }
    return;
  }

  // 新しい XYZ を処理
  previousXYZ = XYZ;
  previousTime = currentTime;  // 処理した時間を記録


  //console.log("Unmatched XYZ. XYZ: ", XYZ);
}

let handleXYZThread = null;
export function startThreadforHandleXYZ() {
  if (handleXYZThread !== null) {
    console.log("[handleXYZThread]: Threads already exists.");
    return;
  }
  
  handleXYZThread = new Thread(() => {
    try {
      while (antiAntiMacroStatus()) {
        const XYZ = getXYZ();
        handleXYZ(XYZ);
        Thread.sleep(1000);
      }
    } catch (e) {
        console.error("Error in thread: ", e);
    }
  });
  handleXYZThread.start();
}


