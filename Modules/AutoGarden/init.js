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

import { Commands } from "./XYZManager";
import { startThreadforHandleXYZ } from "./antiAntiMacro";
import { startAutoGarden, stopAutoGarden } from "./autoGarden";
import { helpMessage } from "./Identifier";

function sendHelpMessage() {
  for (let help of helpMessage) {
    ChatLib.chat(help);
  }
}

const XYZManagements = [
  "setxyz", "removexyz", "listxyz", "getxyz"
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
      Commands(arg1, args);
    } else if (arg1 === "start") {
      startAutoGarden();
    } else if (arg1 === "stop") {
      stopAutoGarden();
    }
  }


}).setName("lc_gardening").setAliases("lcg")

startThreadforHandleXYZ();