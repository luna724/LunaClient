import Settings from "../menu"
import { AntiServerMoving } from "./AntiServerMoving";
import AutobackIsland from "./AutomaticIsland";

register("worldLoad", () => {
  if (Settings.WorldLoadNotice) {
  ChatLib.chat(Settings.WorldLoadNoticeText);
  }

  AutobackIsland();
  AntiServerMoving();
})