import { registerEventListener } from "../../../GriffinOwO/utils/EventListener";
import { autoGardenSetting } from "./gui";

const colored = "§6[§2Auto-Garden§6]: §r§7";
const uncolored = "§7[§8Auto-Garden§7]: §r§7";

function send(message) {
  ChatLib.chat(message);
}


registerEventListener(() => autoGardenSetting.chatConverterEngine,
  register("chat",
    (chat, event) => {
      chat = chat.removeFormatting();

      // 移動方向変更
      if (chat.startsWith("[LC-AutoMove]: Changed direction to")) {
        //let direction = chat.replace("[LC-AutoMove]: Changed direction to", "");
        send(
          uncolored + `Changed direction`
        );
        event.setCanceled(true);
      }

      if (chat === "[AutoMove]: [INFO]: [Main]: Stopped AutoMove") {
        event.setCanceled(true);
      }

      if (chat === "[LC-AutoMove]: AutoMove Stopped.") {
        send(
          uncolored + `Stopping AutoMove`
        );
        event.setCanceled(true);
      }

      if (chat === "[LC-AutoMove]: AutoMove Started.") {
        send(
          uncolored + `Starting AutoMove`
        );
        event.setCanceled(true);
      }

      if (chat.startsWith("[LunaAPI]: ") && chat.endsWith("**LC-Identifier")) {
        let text = chat.replace(
          "[LunaAPI]: ", ""
        ).replace(
          "**LC-Identifier", ""
        );
        send(
          uncolored + text
        );
        event.setCanceled(true);
      }

      if (chat.startsWith("[AutoMove]: [INFO]: [YawChanger]:")) {
        event.setCanceled(true);
      }
    }
  ).setCriteria("${chat}")
);