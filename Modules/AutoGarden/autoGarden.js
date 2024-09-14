import { startTextConverterInAutoGarden, stopTextConverterInAutoGarden } from "./Chat";
import gui from "./gui";
import { header } from "./Identifier";
import { getSessionConfig, saveSessionConfig } from "./module";

export function startAutoGarden() {
  ChatLib.chat(
    header + "§7Started AutoGarden"
  );

  // ステータスを更新
  let sessionConfig = getSessionConfig();
  sessionConfig["status"] = true;
  if (gui.enableAntiAntiMacro) { sessionConfig["antiAntiMacroStatus"] = true; }
  saveSessionConfig(sessionConfig);

  // テキスト変換エンジンを開始
  startTextConverterInAutoGarden();

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

  stopTextConverterInAutoGarden();

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