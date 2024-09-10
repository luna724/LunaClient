import { header, xyzCollection } from "./Identifier";
import { getResizedRotation, getResizedXYZ, checkDirection, getConfig, getNewKey, saveConfig } from "./XYZ/module";

export function valuesNotEnough() {
  // 値が足りない祭に発生させるもの
  // ユーザーへの通知だけで特に必要性はない
  ChatLib.chat(header + "§cMissing an Required arguments.");
}


// 管轄内のコマンドを処理する
export function Commands(trigger, args) {
  console.log("Commands in XYZManager called!");

  if (trigger == "setxyz") {
    if (args.length < 2) {
      // たりないなら
      valuesNotEnough();
      ChatLib.chat(header + "§cRequired: /lcg setXYZ <trigger>");
      return;
    }

    let XYZ = getResizedXYZ();
    let Rotation = getResizedRotation();
    
    let direction = checkDirection(args[1]);
    let config = getConfig();
    let currentValue = config;
    let newKey = getNewKey();
    
    let currentData = [
      newKey, XYZ, Rotation, direction
    ];

    currentValue[newKey] = currentData;
    saveConfig(currentValue);

    ChatLib.chat(
      header + `§aSaved as §l${newKey}§r. §7(XYZ: ${XYZ}, Rotation: ${Rotation}, Direction: ${direction})`
    );
    return;
  }
  
  if (trigger == "removexyz") {
    if (args.length < 2) {
      valuesNotEnough();
      ChatLib.chat(header + "§cRequired: /lcg removeXYZ <targetKey>");
      return;
    }

    let keys = Object.keys(getConfig());
    let key = args[1].toLowerCase();

    if (!keys.includes(key)) {
      ChatLib.chat(header + "§cThat keys NOT found. §7(Tip: keys only contains lowerCase)")
      return;
    }
    
    let currentValue = getConfig();
    console.log(`[/lcg removeXYZ]: Deleted ${JSON.stringify(currentValue[key])}.`);
    delete currentValue[key];

    ChatLib.chat(
      header+ `§aDeleted §l${newKey}§r. §7(Backup in console)`
    );
    return;
  }

  if (trigger == "listxyz") {
    for (let [key, value] of Object.entries(obj)) {
  }
}
}