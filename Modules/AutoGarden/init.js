import "./XYZ/module";
import "./antiAntiMacro";
import "./autoGarden";
import "./Chat";
import "./compare";
import "./Identifier";
import "./module";
import "./Option";
import "./tick";
import "./XYZManager";
import "./gui";
import "./presetManager";
import "./timer";

import { valuesNotEnough, XYZManageCommands } from "./XYZManager";
import { startAutoGarden, stopAutoGarden, toggleAutoGarden } from "./autoGarden";
import { sendHelpMessage } from "./module";
import { collectPresetFromInternet, presetManageCommands } from "./presetManager";
import { commandUsage, header } from "./Identifier";
import { autoGardenSetting } from "./gui";
import { Timer } from "./timer";

const XYZManagements = [
  "setxyz", "removexyz", "listxyz", "getxyz", "currentxyz"
];

register("command", (...args) => {
  if (args === undefined || args.length === 0) {
    sendHelpMessage();
    return;
  }

  const arg1 = args[0]?.toLowerCase();

  if (arg1 === undefined || arg1 === null) {
    sendHelpMessage();
    return;
  } 

  else {
    // args > 1
    if (XYZManagements.includes(arg1)) {
      XYZManageCommands(arg1, args);
    } else if (arg1 === "preset") {
      presetManageCommands(args);
    } else if (arg1 === "start") {
      startAutoGarden();
    } else if (arg1 === "stop") {
      stopAutoGarden();
    } else if (arg1 === "gui") {
      autoGardenSetting.openGUI();
    } else if (arg1 === "toggle") {
      toggleAutoGarden();
    } else if (arg1 === "timer") {
      Timer(args);
    } else if (arg1 === "collect") {
      collectPresetFromInternet(args);
    }
    else {
      valuesNotEnough();
      ChatLib.chat(header + commandUsage);
    }
  }
}).setName("lc_gardening").setAliases("lcg");

register("command", () => {
  ChatLib.command("lcg gui", true);
}).setName("lcgui");