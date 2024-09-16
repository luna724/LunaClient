import { autoGardenSetting } from "./gui";
import { autoGardenTextConverterEngine, header } from "./Identifier";
import { getSessionConfig, saveSessionConfig } from "./module";

export function startAutoGarden() {
  ChatLib.chat(
    header + "§7Started AutoGarden"
  );

  // ステータスを更新
  let sessionConfig = getSessionConfig();
  sessionConfig["status"] = true;
  if (autoGardenSetting.enableAntiAntiMacro) {
    sessionConfig["antiAntiMacroStatus"] = true;
  }
  saveSessionConfig(sessionConfig);

  // テキスト変換エンジンを開始
  autoGardenTextConverterEngine = true;

  // とりま開始
  ChatLib.command("automove start", true);

  // あとは tick.js に託す
}

export function stopAutoGarden(stopText="§7Stopped AutoGarden") {
  ChatLib.chat(
    header + stopText
  );

  // ステータスを更新
  let sessionConfig = getSessionConfig();
  sessionConfig["status"] = false;
  sessionConfig["antiAntiMacroStatus"] = false;
  saveSessionConfig(sessionConfig);

  autoGardenTextConverterEngine = false;

  ChatLib.command("automove stop", true);
}

export function toggleAutoGarden(stopText="§7Stopped AutoGarden") {
  const sessionConfig = getSessionConfig();
  if (sessionConfig) {
    stopAutoGarden(stopText);
  } else {
    startAutoGarden();
  }
}