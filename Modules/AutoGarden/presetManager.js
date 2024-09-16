import { header } from "./Identifier";
import { changeCurrentPreset, getCurrentPreset, getPresetJson, savePresetJson } from "./module";
import { getConfig, saveConfig } from "./XYZ/module";
import { valuesNotEnough } from "./XYZManager";

// 管轄内のコマンドを処理する
export function presetManageCommands(args) {
  // [0] = preset, [1] = trigger, ..
  if (args.length < 2) {
    valuesNotEnough();
    ChatLib.chat(header + "§cRequired: /lcg preset <save/load/delete/rename/current/new>");
    return;
  }
  const trigger = args[1]?.toLowerCase();
  
  if (trigger === "save") {
    if (args.length < 3) {
      valuesNotEnough();
      ChatLib.chat(header + "§cRequired: /lcg preset save <presetName>");
    }
    const presetName = args[2];
    const presetJson = getPresetJson();
    const presets = Object.keys(presetJson);

    if (presetName === "__default") {
      ChatLib.chat(header + "§c__default are system key.");
      return;
    }
    if (presets.includes(presetName)) {
      ChatLib.chat(header + "§6[WARN]: §cThis presetNames already taken.");
      console.log(
        `[presetManager.js:save]: Backup: ${JSON.stringify(presetJson[presetName])}`
      );
    }

    const currentValue = getConfig();
    presetJson[presetName] = currentValue;
    savePresetJson(presetJson);

    ChatLib.chat(
      header + `§aSuccessfully saved preset ${presetName}`
    );
    return;
  }

  if (trigger === "save__") {
    if (args.length < 4) {
      return;
    }
    const presetName = args[2];
    const presetValue = args[3];

    const presetJson = getPresetJson();
    presetJson[presetName] = JSON.parse(presetValue);
    savePresetJson(
      presetJson
    );

    ChatLib.chat(
      header + `§aSuccessfully saved preset ${presetName}`
    );
    return;
  }

  if (trigger === "load") {
    let presetName = null;
    if (args.length < 3) {
      presetName = "__default";
    }
    else { 
      presetName = args[2]; 
    }

    const presetJson = getPresetJson();
    const presets = Object.keys(presetJson);

    if (!presets.includes(presetName)) {
      ChatLib.chat(header + "§cThat presets NOT found.")
      return;
    }

    const configValue = presetJson[presetName];
    const currentValue = getConfig();

    presetJson["__default"] = currentValue;
    saveConfig(configValue);
    changeCurrentPreset(presetName);

    savePresetJson(presetJson);

    ChatLib.chat(
      header + `§aSuccessfully loaded preset ${presetName}`
    );
    return;
  }

  if (trigger === "current") {
    ChatLib.chat(
      header + `§aCurrent Preset: ${getCurrentPreset()}`
    );
    return;
  }

  if (trigger === "delete") {
    if (args.length < 3) {
      valuesNotEnough();
      ChatLib.chat(header + "§cRequired: /lcg preset delete <targetPreset>");
    }

    const presetJson = getPresetJson();
    const presets = Object.keys(presetJson);

    const presetName = args[2];

    if (!presets.includes(presetName)) {
      ChatLib.chat(header + "§cThat presets NOT found.")
      return;
    }

    const backup = presetJson[presetName];  
    console.log(
      `[/lcg preset delete]: Deleted ${JSON.stringify(backup)}`
    );
    delete presetJson[presetName];

    savePresetJson(presetJson);
    ChatLib.chat(
      header + `§aDeleted §l${presetName}§r. §7(Backup in console)`
    );
    return;
  }

  if (trigger === "rename") {
    if (args.length < 4) {
      valuesNotEnough();
      ChatLib.chat(header + "§cRequired: /lcg preset rename <targetPresetName> <resizedPresetName>");
    }

    const presetJson = getPresetJson();
    const presets = Object.keys(presetJson);

    const presetName = args[2];
    const afterPresetName = args[3];

    // targetが存在するかどうかチェック
    if (!presets.includes(presetName)) {
      ChatLib.chat(header + "§cThat presets NOT found.")
      return;
    }

    // afterが存在しないことをチェック
    if (presets.includes(afterPresetName)) {
      ChatLib.chat(header + "§cThis presetNames already taken.");
      return;
    }

    // 条件を満たした場合、削除と保存を行う
    const prvConfigValue = presetJson[presetName];

    ChatLib.command(`lcg preset delete ${presetName}`, true);
    ChatLib.command(`lcg preset save__ ${afterPresetName} ${JSON.stringify(prvConfigValue)}`, true);
  }

  if (trigger === "new") {
    if (args.length < 3) {
      valuesNotEnough();
      ChatLib.chat(header + "§cRequired: /lcg preset new <presetName>");
    }

    const presetName = args[2];

    const presetJson = getPresetJson();
    const presets = Object.keys(presetJson);

    if (presets.includes(presetName)) {
      ChatLib.chat(header + "§cThis presetNames already taken.");
      return;
    }

    presetJson[presetName] = {};
    savePresetJson(presetJson);
    ChatLib.command(`lcg preset load ${presetName}`, true);
  }
}