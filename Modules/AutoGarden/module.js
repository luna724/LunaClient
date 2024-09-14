import { helpMessage } from "./Identifier";

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