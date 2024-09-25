import { ScoreboardGetByString } from "../scoreboard";
import { autoGardenSetting } from "./gui";

class Pests {
  pestCount = 0;
  pestPlots = [];
}
export const PestInfo = new Pests();

/**
 * get Pests count from two-way method.
 * Available: scoreboard (ignoreCase)
 * COMING SOON: TabList    
 * @param {String} gatherMethod 
 */
function getPestCount(gatherMethod="scoreboard") {
  gatherMethod = gatherMethod.toLowerCase();
  let pestCountString = "0";

  if (gatherMethod === "scoreboard") {
    pestCountString = ScoreboardGetByString("⏣")

    pestCountString = pestCountString.replace(
      " ⏣ The Garde🌠n", ""
    );

    if (pestCountString.includes(" ൠ ")) {
      pestCountString = pestCountString.split("ൠ x")[1];
    }
    else {
      pestCountString = "0"
    }

    ChatLib.chat(pestCountString);
  }

  
}




/**
 * check PestTracker is active
 * 
 * @returns {boolean}
 **/
export function pestCheck() {
  const Option = autoGardenSetting.pestTracker;
  getPestCount();

  return Option;
}

/**
 * PestTracker Main
 * call from tick.js
 * 
 * @returns {void}
 */
export function handlePest() {

}