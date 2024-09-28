import { onTick } from "../../ServerTick";

class TabListTracker {
  currentTabList = TabList?.getNames();
}
export const TabListln = new TabListTracker();

/**
 * 
 * @param {String} trigger 
 * @param {String} fail 
 * @param {String[]} lns 
 */
export function TabListGetByString(
  trigger, fail="", lns=null
) {
  if (lns === null) { lns = TabListTracker.currentTabList; } 
  if (lns === undefined) { return fail; }

  const SpecLine = lns.find(
    (line) => line.includes(trigger)
  );

  if (SpecLine === undefined) { return fail; }
  return SpecLine.removeFormatting();
}

onTick(() => {
  const Line = TabList?.getNames();

  if (Line === undefined || Line === null) {
    return;
  }
  TabListln.currentTabList = Line;
})