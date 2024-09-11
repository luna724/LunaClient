import "./XYZManager";
import "./tick";
import "./Option";
import "./Identifier";

import { Commands } from "./XYZManager";

const helpMessage = [
  "§a--- §dLunaClient §7/ §2Auto-Garden§a ---",
  "§a- 各機能詳細は README (/lc_readme) 参照-",
  "§a- /lc_gardening start          -",
  "§a- スタートする                  -",
  "§a- /lc_gardening setxyz <trigger>",
  "§a- 現在地をフラグする。          -", 
  "§a- <trigger>には /automove setdirection の第二引数が入り、移動方向を指定する。-",
  "§a- /lc_gardening removexyz <targetkey>",
  "§a- フラグした場所を削除する      -",
  "§a- targetkey は setxyz の際にのみ確認可能 -",
  "§a- /lc_gardening listxyz        -",
  "§a- フラグしたxyzのxyzYPとキーを表示する",
  "§a- /lc_gardening preset save <preset>",
  "§a- プリセットの保存              -",
  "§a- /lc_gardening preset load <preset>",
  "§a- プリセットの読み込み           -",
  "§a- /lc_gardening preset delete <preset>",
  "§a- プリセットの削除               -",
  "§a- /lc_gardening preset rename <presetBefore> <presetAfter>",
  "§a- プリセット名の更新             -",
  "§a- /lc_gardening getxyz <targetkey>",
  "§a- 指定したキーのみの xyzYP を表示する",
  "§a- /lc_gardening help            -",
  "§a- このテキストを表示             -",
  "§a---------------------------------"
];
function sendHelpMessage() {
  for (let help of helpMessage) {
    ChatLib.chat(help);
  }
}

const XYZManagements = [
  "setxyz", "removexyz", "listxyz", "getxyz"
];

register("command", (...args) => {
  if (args === undefined || args.length === 0) {
    sendHelpMessage();
    return;
  }

  const arg1 = args[0]?.toLowerCase();

  if (arg1 === undefined || arg1 === null) {
    sendHelpMessage();
    return;
  } 

  else {
    // args > 1
    if (XYZManagements.includes(arg1)) {
      Commands(arg1, args);
    }
  }


}).setName("lc_gardening").setAliases("lcg")