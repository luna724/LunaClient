import "./Modules/worldLoad";
import "./Modules/TreasureTalismanFlipTracker";
import "./Modules/Chats/AHPM_Optimizer";
import "./Modules/Chat";
import "./Modules/LunaAPI/flipTrackHelper";
import "./Modules/AutoMove";
import "./Modules/CommandShortcut";

import "./Modules/AutoGarden/init";

import Settings from "./menu";

register("command", () => {
  Settings.openGUI()
}).setName('lunaclient').setAliases("lc", "luna")


register("command", () => {
  ChatLib.chat("No Response.");
}).setName("lc_test")