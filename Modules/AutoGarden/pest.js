import { ScoreboardGetByString } from "../scoreboard";
import { autoGardenSetting } from "./gui";

class Pests {
  pestCount = 0;
  pestPlots = [];
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
    ChatLib.chat("[PestTracker]: WARN");
  }

  if (pestCount === pestAllowStop || (pestCount >= pestAllowWarn && pestAllowStop === -1)) {
    ChatLib.chat("[PestTracker]: STOP");
    ChatLib.command("lcg stop", true);
  }
}