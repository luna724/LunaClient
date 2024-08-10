import "./Modules/worldLoad";
import "./Modules/TreasureTalismanFlipTracker";
import "./Modules/Chats/AHPM_Optimizer";
import "./Modules/Chat";
import "./Modules/BinSniper/index";
import "./Modules/LunaAPI/flipTrackHelper";

import Settings from "./menu";
import { request } from "axios";

register("command", () => {
  Settings.openGUI()
}).setName('lunaclient').setAliases("lc", "luna")