import { onTick } from "../../ServerTick";

class ScoreboardTracker {
  currentBoard = Scoreboard?.getLines();
}
export const Scoreboardln = new ScoreboardTracker();

/**
 * get Specify lines Scoreboard String by Text
 * 
 * @param {String} trigger 
 * @param {String[]} lns 
 * @returns {String}
 */
export function ScoreboardGetByString(
  trigger, lns=null
) {
  if (lns == null) { lns = Scoreboardln.currentBoard }

  const SpecLine = lns.find(
    (line) => line.getName().includes(trigger)
  );
  return SpecLine.getName().removeFormatting();
}

onTick(() => {
  const Line = Scoreboard?.getLines()

  if (Line === undefined || Line === null) {
    return;
  }
  Scoreboardln.currentBoard = Line;
})