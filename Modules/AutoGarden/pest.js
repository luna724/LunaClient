import { ScoreboardGetByString } from "../scoreboard";
import { stopAutoGarden } from "./autoGarden";
import { autoGardenSetting } from "./gui";
import { header } from "./Identifier";

class Pests {
  pestCount = 0;
  repellentType = ""; // MAX or Normal
  repellentRemain = "";
}
export const PestInfo = new Pests();

/**
 * get Pests count from two-way method.
 * Available: Scoreboard (ignoreCase)
 * 
 * @param {String} gatherMethod 
 */
function getPestCount(gatherMethod="scoreboard") {
  gatherMethod = gatherMethod.toLowerCase();
  let pestCountString = "";
  let pestCount = 0;

  if (gatherMethod === "scoreboard") {
    pestCountString = ScoreboardGetByString("⏣");

    pestCountString = pestCountString.replace(
      " ⏣ The Garde🌠n", ""
    );

    if (pestCountString.includes(" ൠ ")) {
      pestCountString = pestCountString.split("ൠ x")[1];
    }
    else {
      pestCountString = "0"
    }

    //ChatLib.chat(pestCountString);
  }

  const stringConvertable = /^\d+$/.test(pestCountString);
  if (stringConvertable) {
    pestCount = parseInt(pestCountString);
  }

  PestInfo.pestCount = pestCount;
  return pestCount;
}

/**
 * check PestTracker is active
 * 
 * @returns {boolean}
 **/
export function pestCheck() {
  const Option = autoGardenSetting.pestTracker;

  return Option;
}

/**
 * PestTracker Main
 * call from tick.js
 * 
 * @returns {void}
 */
export function handlePest() {
  const pestCount = getPestCount();

  if (pestCount === 0) { return; }

  const pestAllowWarn = autoGardenSetting.pestAllowed;
  const pestAllowStop = autoGardenSetting.pestAllowedInAutoStop; 

  if (pestCount >= pestAllowWarn) {
    World.playSound("mob.cat.meow", 100, 1.15);
    //Client.showTitle(`§ePest Counts reached ${pestCount.toString()}!`)
    
    ChatLib.chat(`§ePest Counts reached ${pestCount.toString()}!`);
    Thread.sleep(50);
  }

  if (pestCount === pestAllowStop || (pestCount >= pestAllowWarn && pestAllowStop === -1)) {
    ChatLib.chat(header + `§c[PestTracker]: reached ${pestCount}`);
    stopAutoGarden("§cStopped AutoGarden by Pest counts");
    return;
  }
}


/**
 * Pest Repellent Check
 */
