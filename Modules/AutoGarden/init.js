import "./XYZManager";
import { Commands } from "./XYZManager";

const helpMessage = [
  "--- LunaClient / Auto-Garden ---",
  "- 各機能詳細は README (/lc_readme) 参照-",
  "- /lc_gardening start          -",
  "- スタートする                  -",
  "- /lc_gardening setxyz <trigger>",
  "- 現在地をフラグする。          -", 
  "- <trigger>には /automove setdirection の第二引数が入り、移動方向を指定する。-",
  "- /lc_gardening removexyz <targetkey>",
  "- フラグした場所を削除する      -",
  "- targetkey は setxyz の際にのみ確認可能 -",
  "- /lc_gardening listxyz        -",
  "- フラグしたxyzのxyzYPとキーを表示する",
  "- /lc_gardening preset save <preset>",
  "- プリセットの保存              -",
  "- /lc_gardening preset load <preset>",
  "- プリセットの読み込み           -",
  "- /lc_gardening preset delete <preset>",
  "- プリセットの削除               -",
  "- /lc_gardening preset rename <presetBefore> <presetAfter>",
  "- プリセット名の更新             -",
  "- /lc_gardening getxyz <targetkey>",
  "- 指定したキーのみの xyzYP を表示する",
  "- /lc_gardening help            -",
  "- このテキストを表示             -",
  "---------------------------------"
];

const XYZManagements = [
  "setxyz", "removexyz", "listxyz", "getxyz"
];

register("command", (args) => {
  if (args.length < 1 || args[0].toLocaleLowerCase() === "help") {
    for (help in helpMessage) {
      ChatLib.chat(help);
    }
    return;

  } 
  else {
    // args > 1
    let arg1 = args[0].toLowerCase();
    if (XYZManagements.includes(arg1)) {
      Commands(arg1, args);
    }
  }


}).setName("lc_gardening").setAliases("lcg")