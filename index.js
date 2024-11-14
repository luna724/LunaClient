import "./Modules/worldLoad";
import "./Modules/Chats/AHPM_Optimizer";
import "./Modules/Chat";
import "./Modules/AutoMove";
import "./Modules/CommandShortcut";

// import "./Modules/LunaAPI/flipTrackHelper";
// import "./Modules/LunaAPI/TreasureTalisman";

import "./Modules/AutoGarden/init";

import Settings from "./menu";

register("command", () => {
  Settings.openGUI()
}).setName('lunaclient').setAliases("lc")


register("command", () => {
  ChatLib.chat("");
}).setName("lc_test")