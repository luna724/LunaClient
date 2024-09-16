import { header, helpMessage } from "./Identifier";

export function getSessionConfig() {
  // auto_garden.session.json を返す
  const rawJson = JSON.parse(
    FileLib.read("LunaClient", "auto_garden.session.json")
  );
  return rawJson;
}

export function saveSessionConfig(newcfg) {
  const backup = getSessionConfig();
  console.log(
    `[module.js:saveSessionConfig]: Backup: ${JSON.stringify(backup)}`
  );

  FileLib.write("LunaClient", "auto_garden.session.json", JSON.stringify(newcfg));
}

export function sendHelpMessage() {
  for (let help of helpMessage) {
    ChatLib.chat(help);
  }
}

// Preset
export function getPresetJson() {
  const rawJson = JSON.parse(
    FileLib.read("LunaClient", "presets/auto_garden.presets.json")
  );
  return rawJson;
}

export function savePresetJson(newcfg) {
  const backup = getPresetJson();
  console.log(
    `[module.js:savePresetJson]: Backup: ${JSON.stringify(backup)}`
  );

  FileLib.write("LunaClient", "presets/auto_garden.presets.json", JSON.stringify(newcfg));
}

export function changeCurrentPreset(currentPreset) {
  let sessionConfig = getSessionConfig();
  const presetJson = getPresetJson();
  const presets = Object.keys(presetJson);
  
  if (!presets.includes(currentPreset)) {
    console.log(
      `[ERR]: [module.js:changeCurrentPreset]: new CurrentPreset aren't in preset.json! (${currentPreset})`
    );

    ChatLib.chat(
      header + `§can error occurred in preset System. see console for more details.`
    );
    currentPreset = "__default";
  }
  
  console.log(
    `[module.js:changeCurrentPreset]: Preset Changed!: ${currentPreset}`
  );

  sessionConfig["currentPreset"] = currentPreset;
  savePresetJson(sessionConfig);
}

export function getCurrentPreset() {
  const sessionConfig = getSessionConfig();
  return sessionConfig["currentPreset"];
}