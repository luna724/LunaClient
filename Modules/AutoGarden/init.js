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

import { valuesNotEnough, XYZManageCommands } from "./XYZManager";
import { startAutoGarden, stopAutoGarden, toggleAutoGarden } from "./autoGarden";
import gui from "./gui";
import { sendHelpMessage } from "./module";
import { presetManageCommands } from "./presetManager";
import { commandUsage, header } from "./Identifier";

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
      gui.openGUI();
    } else if (arg1 === "toggle") {
      toggleAutoGarden();
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