import { checkInWorld } from "../../GriffinOwO/utils/Location";

class AntiServerMove {
  isEnable = false;

  typeMainServer = null;
}

AntiServerMoveOpt = new AntiServerMove();

function killEvent() {
  let killTarget = [
    "autogaden", "automove"
  ]
  for (let kill of killTarget) {
    Thread.sleep(100);
    ChatLib.command(`lunaclient_terminate_event ${kill}`);
  }
}


export function AntiServerMoving() {
  if (!AntiServerMoveOpt.isEnable) return;
  if (checkInWorld(AntiServerMoveOpt.typeMainServer)) return;

};


export default function ActivateAntiServerMoving(mainServer="Manual") {
  /* Force activate AntiServerMoving*/

  ChatLib.chat("[§dLunaClient§f]: §7Starting AntiServerMoving..");
  AntiServerMoveOpt.isEnable = true;
  
  // typeMainServer を取得
  if (mainServer == "Manual") {
    Thread.sleep(50);
    const WorldLine = TabList?.getNames()?.find(tab => tab.includes("Area"));
    if (WorldLine) {
        currentWorld = WorldLine.replace("Area: ", "").removeFormatting();
    }
    else {
      ChatLib.chat("[§dLunaClient§f]: §7Unknown World!");
    }
  }
  else {
    currentWorld = mainServer;
  }
  AntiServerMoveOpt.typeMainServer = currentWorld;
}