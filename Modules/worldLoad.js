import Settings from "../menu"
import AutobackIsland from "./AutomaticIsland";

register("worldLoad", () => {
  if (Settings.WorldLoadNotice) {
  ChatLib.chat(Settings.WorldLoadNoticeText);
  }

  AutobackIsland();
})