import Settings from "../menu"
import { checkInZone, checkInWorld } from "../../GriffinOwO/utils/Location"

export default function AutobackIsland() {
  if (!Settings.autoBacktoIsland) return;
  
  let targetIslandWarpCommand = "";
  let islandWorldName = "";

  if (Settings.autoBackIslandType === 0) {
    targetIslandWarpCommand = "is";
    islandWorldName = "Your Island";
  } else if (Settings.autoBackIslandType === 1) {
    targetIslandWarpCommand = "warp garden";
    islandWorldName = "Garden"
  }
  
  // とりあえずうってみる
  ChatLib.chat("[§dLunaClient§f]: §7Trying to back to island..")
  new Thread(() => { // 他のスレッドを妨害しない
    Thread.sleep(2500)
    if (checkInWorld(islandWorldName)) {
      ChatLib.chat(`[§dLunaClient§f]: §7You're already on §a${islandWorldName}`)
      return // 拠点ならパス
    }
    else if (checkInWorld("Hub")) {
      ChatLib.command(targetIslandWarpCommand) // ハブなら /is
    }
    else if (!checkInWorld("Unknown")) {
      // hub, is 以外のsbでも /is
      ChatLib.command(targetIslandWarpCommand);
    }
    else {
      // SB外の処理
      ChatLib.chat("[§dLunaClient§f]: §7Waiting a few seconds for §c\"Please don't spam the command!\"");
      Thread.sleep(10000);
      ChatLib.command("play sb");
      }
    }).start();
}