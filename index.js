import "./Modules/worldLoad";
import "./Modules/TreasureTalismanFlipTracker";
import "./Modules/Chats/AHPM_Optimizer";
import "./Modules/Chat";
import "./Modules/BinSniper/index";
import "./Modules/LunaAPI/flipTrackHelper";
import "./Modules/AutoMove";
import "./Modules/CommandShortcut";

import Settings from "./menu";
import { request } from "axios";

register("command", () => {
  Settings.openGUI()
}).setName('lunaclient').setAliases("lc", "luna")

register("command", () => {
  // Test command
}).setName("lc_test")