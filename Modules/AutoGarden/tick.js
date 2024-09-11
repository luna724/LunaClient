import { xyzCollection } from "./Identifier";
import { getStatus, getTemporaryConfig } from "./Option"
import { getXYZ } from "./XYZ/module";


function triggered(collection) {
  const Rotation = collection[2];
  const Yaw = Rotation[0];
  const Pitch = Rotation[1];
  const afterMove = collection[3];

  ChatLib.command(`automove sendstatus senddatatolunaclient info triggered AutoGarden in here`);

  // 停止
  ChatLib.command("automove stop", true);

  // 設定の変更
  ChatLib.command(`automove setdirection ${afterMove}`, true);

  // Yaw を変更しながら移動開始
  ChatLib.command(`automove setyaw  ${Yaw.toString()}`, true);
  ChatLib.command(`automove start`, true);
}


register("tick", (elapsed) => {
  // 有効状態なら実行
  if (!getStatus()) { return; }
  if (Player?.getX() === undefined) {
    console.log("Player object is not initialized yet.");
    return;
  }

  const cfg = getTemporaryConfig();
  const XYZCollections = cfg;
  const triggerXYZs = Object.values(XYZCollections).map(v => v[1]);
  
  // プレイヤーの現在地
  // try {
  //   const XYZ = getXYZ();
  // } catch (e) {
  //   console.error("Failed in player XYZ: ", e);
  //   return;
  // }
  // const XYZ = getXYZ();
  
  // XYZがtriggerXYZsに含まれているかチェック
  if (triggerXYZs.some(t => JSON.stringify(t) === JSON.stringify(XYZ))) {
    // 一致した場合、XYZCollectionsの値を取得
    const triggeredXYZ = Object.entries(XYZCollections).find(([key, value]) => 
      JSON.stringify(value[1]) === JSON.stringify(XYZ));
    
    triggered(triggeredXYZ);
  }
})