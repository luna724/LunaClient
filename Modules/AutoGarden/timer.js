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
    afterCommand = args.slice(2);
    const afterCommand = afterCommand.join(" ");
  }

  // time を正則化
  time = parseInt(time);
  
  if (!time || typeof time !== "number") {
    ChatLib.chat(header + "§c<time:sec> must be Integer (e.g. 30)");
    return;
  }
  time *= 1000

  // 新規スレッドで終わるまで待機
  const AutoGardenTimerThread = new Thread(
    () => {
      ChatLib.chat(header + `§a${time.toString()}s Timer started! §7AfterCommand: (${afterCommand})`);
      Thread.sleep(time);
      ChatLib.chat(header + `§aTimer Ended (${afterCommand})`);
      ChatLib.command(afterCommand.replace(/^\/+|\/+$/g, ""), true);
    }
  ).start();
}