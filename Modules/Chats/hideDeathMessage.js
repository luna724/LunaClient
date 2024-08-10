import Settings from "../../menu";
import { checkInWorld } from "../../../GriffinOwO/utils/Location";

export default function hideDeathMessage(
  chat, event
) {
  let chat = chat.removeFormatting();
  
  // dungeon ならパス   
  if (Settings.hideDeathMessageExceptDungeon && checkInWorld("Dungeon")) return;

  if (chat.startsWith(" ☠ ") && chat.endsWith(".")) {
    event.setCanceled(true);
  }
}