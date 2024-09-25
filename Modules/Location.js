/* This Code copied from GriffinOwO
  in GriffinOwO/utils/Location.js

  Rifts NOT Supported
  */
let currentWorld = "Unknown";
let worldRetryCount = 0;
let zoneRetryCount = 0;

function checkCurrentWorld() {
    const WorldLine = TabList?.getNames()?.find(tab => tab.includes("Area"));
    if (WorldLine) {
        currentWorld = WorldLine.replace("Area: ", "").removeFormatting();
        zoneRetryCount = 0;
    } else {
        worldRetryCount++;
        if (worldRetryCount < 10) {
            setTimeout(checkCurrentWorld, 1000);
        } else {
            currentWorld = "Unknown";
            //ChatLib.chat("Failed to get current world after multiple attempts.");
        }
    }
};

register("worldLoad", () => {
  worldRetryCount = 0;
  checkCurrentWorld();
});

register("worldUnload", () => {
  currentZone = "unknown";
});

export function checkInWorld(world) {
  return currentWorld === world;
}

export function checkInZone(zone) {
  throw "[LunaClient]: checkInZone's NOT SUPPORTED.";
}
