import { header } from "./Identifier";
import { getResizedRotation, getResizedXYZ, checkDirection, getConfig } from "./XYZ/module";

export function valuesNotEnough() {
  // 値が足りない祭に発生させるもの
  // ユーザーへの通知だけで特に必要性はない
  ChatLib.chat(header + "Missing an Required arguments.");
}


// 管轄内のコマンドを処理する
export function Commands(trigger, args) {
  if (trigger == "setxyz") {
    if (args.length < 1) {
      // たりないなら
      valuesNotEnough();
      ChatLib.chat(header + "Required: /lcg setxyz <trigger>");
      return;
    }

    let XYZ = getResizedXYZ();
    let Rotation = getResizedRotation();
    
    let direction = checkDirection(args[1]);
    let config = getConfig();
    let currentValue = config["xyzCollection"];
    let newKey = getNewKey();
    
    let currentData = [
      newKey, XYZ, Rotation, direction
    ];

    currentValue[newKey] = currentData;
  }

}