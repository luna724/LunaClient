import "./Modules/worldLoad";
import "./Modules/TreasureTalismanFlipTracker";
import "./Modules/Chats/AHPM_Optimizer";
import "./Modules/Chat";
import "./Modules/BinSniper/index";
import "./Modules/LunaAPI/flipTrackHelper";
import "./Modules/AutoMove";
import "./Modules/CommandShortcut";
import "./Modules/AutoGarden/init";
import "./Modules/AutoGarden/XYZ/module";

import Settings from "./menu";
import { getResizedXYZ, getRotation } from "./Modules/AutoGarden/XYZ/module";

register("command", () => {
  Settings.openGUI()
}).setName('lunaclient').setAliases("lc", "luna")

register("command", () => {
  ChatLib.chat(getRotation().toString());
}).setName("lc_test");