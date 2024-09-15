import { header } from "./Identifier";
import { getResizedRotation, getResizedXYZ, checkDirection, getConfig, getNewKey, saveConfig } from "./XYZ/module";

export function valuesNotEnough() {
  // 値が足りない祭に発生させるもの
  // ユーザーへの通知だけで特に必要性はない
  ChatLib.chat(header + "§cMissing an Required arguments.");
}


// 管轄内のコマンドを処理する
export function XYZManageCommands(trigger, args) {
  //console.log("Commands in XYZManager called! trigger&args: "+trigger+args);

  if (trigger === "setxyz") {
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

    if (direction === "ERR") {
      return;
    }

    currentValue[newKey] = currentData;
    saveConfig(currentValue);

    ChatLib.chat(
      header + `§aSaved as §l${newKey}§r. §7(XYZ: ${XYZ}, Rotation: ${Rotation}, Direction: ${direction})`
    );
    return;
  }
  
  if (trigger === "removexyz") {
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

    saveConfig(currentValue);

    ChatLib.chat(
      header + `§aDeleted §l${key}§r. §7(Backup in console)`
    );
    return;
  }

  if (trigger === "listxyz") {
    const config = getConfig();
    // console.log("Config:", config);

    if (!config || typeof config !== "object") {
      console.error("Invalid config object");
      return;
    }

    ChatLib.chat(
      header + "§aAll XYZ in current presets (${currentPresets})"
    );
    ChatLib.chat(".");
    for (let key in config) {
      if (config.hasOwnProperty(key)) {  // 継承プロパティを除外する
        // console.log(`${key}: ${config[key]}`);
        let value = config[key];

        let xyz = value[1];
        let yp = value[2];
        let direction = value[3];

        let X = xyz[0];
        let Y = xyz[1];
        let Z = xyz[2];
        let Yaw = yp[0]; 

        ChatLib.chat(
          `§7[§e${key}§7]: §r§7(X: §f${X}§7, Y: §f${Y}§7, Z: §f${Z}§7, Yaw: §f${Yaw}§7, Direction: §2${direction}§7)` 
        );
      }
    }
    return;
  }

  if (trigger === "getxyz") {
    if (args.length < 2) {
      valuesNotEnough();
      ChatLib.chat(header + "§cRequired: /lcg getxyz <targetKey>");
      return;
    }

    let keys = Object.keys(getConfig());
    let key = args[1].toLowerCase();

    if (!keys.includes(key)) {
      ChatLib.chat(header + "§cThat keys NOT found. §7(Tip: keys only contains lowerCase)")
      return;
    }
    
    let currentValue = getConfig();
    const keyValue = currentValue[key];

    const xyz = keyValue[1];
    const yp = keyValue[2];
    const direction = keyValue[3];
    let X = xyz[0];
    let Y = xyz[1];
    let Z = xyz[2];
    let Yaw = yp[0]; 

    ChatLib.chat(
      `§7[§e${key}§7]: §r§7(X: §f${X}§7, Y: §f${Y}§7, Z: §f${Z}§7, Yaw: §f${Yaw}§7, Direction: §2${direction}§7)` 
    );
    return;
  }

  if (trigger === "currentxyz") {
    let triggeredKey = null;
    let XYZ = getResizedXYZ();
    let currentValue = getConfig();

    for (let key in currentValue) {
      if (currentValue.hasOwnProperty(key)) {
        let value = currentValue[key];
        let configXYZ = value[1];

        if (JSON.stringify(configXYZ) === JSON.stringify(XYZ)) {
          triggeredKey = key;
          break;
        }
      }
    }

    if (triggeredKey === null) {
      ChatLib.chat(
        header + `§9Triggered XYZ hasn't here!`
      );
      return;
    }

    let keyValue = currentValue[triggeredKey];
    const xyz = keyValue[1];
    const yp = keyValue[2];
    const direction = keyValue[3];
    let X = xyz[0];
    let Y = xyz[1];
    let Z = xyz[2];
    let Yaw = yp[0]; 

    ChatLib.chat(
      header + `T§9first Triggered: §7[§a${triggeredKey}§7]: §r§7(X: §f${X}§7, Y: §f${Y}§7, Z: §f${Z}§7, Yaw: §f${Yaw}§7, Direction: §2${direction}§7)`
    );
    return;
  }
}