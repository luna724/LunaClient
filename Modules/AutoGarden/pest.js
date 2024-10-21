import { ScoreboardGetByString } from "../scoreboard";
import { TabListGetByString } from "../tab";
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

function setAvailable() {
  let rawString = "def";

  rawString = TabListGetByString("Repe", "failed.");

  ChatLib.chat(rawString);
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
 * check Repellent is active
 * 
 * @returns {boolean}
 */
export function repellentCheck() {
  return false;
}


/**
 * PestTracker Main
 * call from tick.js
 * 
 * @returns {void}
 */
export function handlePest() {
  const pestCount = getPestCount();

  setAvailable();

  if (pestCount === 0) { return; }

  const pestAllowWarn = autoGardenSetting.pestAllowed;
  const pestAllowStop = autoGardenSetting.pestAllowedInAutoStop; 

  if (pestCount >= pestAllowWarn) {
    World.playSound("mob.cat.meow", 100, 1.15);
    //Client.showTitle(`§ePest Counts reached ${pestCount.toString()}!`)
    
    ChatLib.chat(`§ePest Counts reached ${pestCount.toString()}!`);
    // Thread.sleep(50);
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
export function handleRepellent() {
  
}