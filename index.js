import "./Modules/worldLoad";
import "./Modules/TreasureTalismanFlipTracker";
import "./Modules/Chats/AHPM_Optimizer";
import "./Modules/Chat";
import "./Modules/BinSniper/index";
import "./Modules/LunaAPI/flipTrackHelper";
import "./Modules/AutoMove";
import "./Modules/CommandShortcut";

import "./Modules/AutoGarden/compare";
import "./Modules/AutoGarden/Identifier";
import "./Modules/AutoGarden/module";
import "./Modules/AutoGarden/XYZ/module";
import "./Modules/AutoGarden/Option";
import "./Modules/AutoGarden/XYZManager";
import "./Modules/AutoGarden/tick";
import "./Modules/AutoGarden/init";

import Settings from "./menu";
register("command", () => {
  Settings.openGUI()
}).setName('lunaclient').setAliases("lc", "luna")

register("command", () => {
  ChatLib.chat("HELLO< WORLD!!!");
}).setName("lc_test")