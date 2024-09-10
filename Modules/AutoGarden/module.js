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

export function getPrimaryConfig() {
  // auto_garden.json
  const rawJson = JSON.parse(
    FileLib.read("LunaClient", "auto_garden.json")
  );
  return rawJson;
}

export function savePrimaryConfig(newcfg) {
  backup = getPrimaryConfig();
  console.log(
    `[module.js:savePrimaryConfig]: Backup: ${backup}`
  );

  FileLib.write("LunaClient", "auto_garden.json", JSON.stringify(newcfg));
}