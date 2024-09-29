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



/** From below, use for Preset Collecting 
*/
import { request } from "axios";

/**
 *  
 * @param {string} user 
 * @param {string} repo 
 * @param {string} branch 
 * @param {string} at 
 * @param {string} fn 
 * 
 * @returns {Object} JSON.parse() outputs (JSON.parse(collected_files[f]))
 */
function collectPresetFromGitHub(
  fn="lcgWheatFarmPreset20240929", repo="luna724/luna724", branch="main", at="data/lcg.preset/"
) {
  const dataDir = `https://raw.githubusercontent.com/${repo}/${branch}/${at}${fn}.lcg.presets.json`

  // リクエストを作成
  request({
    url: dataDir,
    method: "GET",
    headers: {
      'Content-Type': 'application/json',
    }
  })
    .then(response => {
      const obj = response.data; // 取得したJSONデータ
    })
    .catch(error => {
      ChatLib.chat(header + "§cRequest failed.  maybe.. Files not found?")
      console.error(error);
    });
  
  
  try {
    const response = JSON.parse(obj);
    ChatLib.chat(header + "§7Request successfully completed");
    return response;
  }
  catch (error) {
    ChatLib.chat(header + "§cRequest failed. see console for more information");

    console.error(
      `LunaClient/AutoGarden/PresetManager Failed parsing JSON.\n
      Object information: typeof ${typeof obj}
      Error: ${error.toString()}
      
      create issue at luna724/LunaClient with this message for help to fix this error` 
    );
    return null;
  }
}


/**
 * returns video preset data for presetName 
 * @param {String} presetName 
 */
function VideoPreset(presetName) {
  const available = [
    "wheat"
  ];

  if (!available.includes(presetName.toLowerCase())) {
    ChatLib.chat(header + "§cUnknown VideoPreset: "+presetName);
    return null;
  }

  const data = {
    "wheat": "lcgWheatFarmPreset20240929"

  };

  const targetFn = data[presetName.toLowerCase()];
  return targetFn;
}

function collectPreset(
  preset="custom", saveName="" //, customRepo="Example/repo", customDir="directory/example", customFile="customFile"
) {
  if (preset !== "custom") {
    const compressd = VideoPreset(preset);
    // user, repo, branch, at, fn

    if (compressd === null) { return; } 
    const obj = collectPresetFromGitHub(
      compressd
    );

    if (obj === null) { return; }

    const presetData = obj.f;
    const title = obj.title;
    const fn = obj.fn;

    if (saveName === "") { saveName = title; }

    ChatLib.chat(header + "§aCollected object for "+fn+"!");
    
    ChatLib.command("lcg preset save __TMP__", true);
    Thread.sleep(50);
    
    saveConfig(presetData);
    ChatLib.command(`lcg preset save ${saveName}`, true);
    Thread.sleep(50);

    ChatLib.command("lcg preset load __TMP__", true);

    ChatLib.chat(header+"§aCompleted!");
    ChatLib.command(`lcg preset load ${saveName}`, true);
    return;
  }

}

export function collectPresetFromInternet(args) {
  if (args.length < 2) {
    valuesNotEnough();
    ChatLib.chat(header + "§cRequired: /lcg collect <Name> (saveName)");
    return;
  }
  const name = args[1];
  let saveName = "";

  if (args.length < 3) {
    saveName = args[2];
  }

  collectPreset(name, saveName);
}