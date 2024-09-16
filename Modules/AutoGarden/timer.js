import { valuesNotEnough } from "./XYZManager";

export function Timer(args) {
  if (args.length < 2) {
    valuesNotEnough();
    ChatLib.chat(header + "§cRequired: /lcg timer <time:sec> (afterCommand?=lcg stop)");
    return;
  }

  let time = args[1];
  let afterCommand = "lcg stop";

  if (args.length > 2) {
    afterCommand = args[2];
  }

  // time を正則化
  time = parseInt(time);
  
  if (!time || typeof time !== "number") {
    ChatLib.chat(header + "§c<time:sec> must be Integer (e.g. 30)");
    return;
  }

  // 新規スレッドで終わるまで待機
  const AutoGardenTimerThread = new Thread(
    () => {
      Thread.sleep(time);
      ChatLib.command(afterCommand.replace(/^\/+|\/+$/g, ""), true);
    }
  ).start();
}