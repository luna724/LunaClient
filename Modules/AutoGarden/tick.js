import { handleXYZ } from "./antiAntiMacro";
import { compareXYZ } from "./compare";
import { antiAntiMacroStatus, getStatus } from "./Option"
import { handlePest, pestCheck } from "./pest";
import { getConfig, getResizedXYZ, getXYZ } from "./XYZ/module";


function XYZMatched(collection) {
  collection = collection[1]; // 値を摘出

  const Rotation = collection[2];
  const Yaw = Rotation[0];
  const Pitch = Rotation[1];
  const afterMove = collection[3];

  //ChatLib.command(`automove sendstatus senddatatolunaclient info triggered AutoGarden in here`);

  // 停止
  ChatLib.command("automove stop", true);

  if (afterMove.toLowerCase() === "spawn") {
    ChatLib.command("gd", true);
    ChatLib.command(`automove rotateYaw senddatatolunaclient ${Yaw.toString()} 2`, true);
    return;
  }

  // 設定の変更
  ChatLib.command(`automove setdirection ${afterMove}`, true);

  // Yaw を変更しながら移動開始
  ChatLib.command(`automove rotateYaw senddatatolunaclient ${Yaw.toString()} 2`, true);
  ChatLib.command(`automove start`, true);
}


register("tick", (elapsed) => {
  // 有効状態なら実行
  if (!getStatus()) { return; }
  if (Player?.getX() === undefined) {
    console.log("Player object is not initialized yet.");
    return;
  }

  const cfg = getConfig();
  const XYZCollections = cfg;
  const triggerXYZs = Object.values(XYZCollections).map(v => v[1]);
  
  // プレイヤーの現在地
  try {
    const XYZ = getXYZ();
  } catch (e) {
    console.error("Failed in player XYZ: ", e);
    return;
  }
  const rawXYZ = getXYZ();
  const XYZ = getResizedXYZ(rawXYZ);

  if (antiAntiMacroStatus()) { handleXYZ(rawXYZ); }
  if (pestCheck()) { handlePest(); }
  
  // XYZがtriggerXYZsに含まれているかチェック
  if (triggerXYZs.some(t => compareXYZ(t, XYZ))) {
    // 一致した場合、XYZCollectionsの値を取得
    const triggeredXYZ = Object.entries(XYZCollections).find(([key, value]) => 
      compareXYZ(value[1], XYZ));

    XYZMatched(triggeredXYZ);
  }
})