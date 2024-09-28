import { onTick } from "../../ServerTick";

class ScoreboardTracker {
  currentBoard = Scoreboard?.getLines();
}
export const Scoreboardln = new ScoreboardTracker();

/**
 * get Specify lines Scoreboard String by Text
 * 
 * @param {String} trigger Trigger string
 * @param {String} fail SpecLine === undefined: return fail
 * @param {Array} lns Scoreboard.getLines() custom value
 * @returns {String}
 */
export function ScoreboardGetByString(
  trigger, fail="", lns=null
) {
  if (lns === null) { lns = Scoreboardln.currentBoard }
  if (lns === undefined) { return fail; }

  const SpecLine = lns.find(
    (line) => line.getName().includes(trigger)
  );

  if (SpecLine === undefined) { return fail; }

  return SpecLine.getName().removeFormatting();
}

onTick(() => {
  const Line = Scoreboard?.getLines()

  if (Line === undefined || Line === null) {
    return;
  }
  Scoreboardln.currentBoard = Line;
})