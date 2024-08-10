import binSniperOpts from "./shared";
import Snipe from "./Snipe";

function Sniper(
  itemName, maxValue, minValue, delay, autoStop, sleepOptimize
) {
  new Thread(() => { // スレッディング処理
    let index = "[§dLunaClient§f]:"

    ChatLib.chat(`${index} Start BinSniping..`);
    
    console.log(`[BS]: getStatus: ${binSniperOpts.getStatus()}`);
    /*
    TODO:
    オークションを開く <<< Working
    アイテムを取得
    購入できる価格なら購入
    sleep中なら無視 (ただしそれしかないなら連打で購入 / 設定で変更)
    購入成功/失敗でGUIが閉じたら
    -> ループ 
    */

    function runSniper() {
      Snipe(itemName, maxValue, minValue, delay, autoStop, sleepOptimize);
    }
    runSniper()
    
  }).start()
}

export default function startSniper (values) {
  let itemName = values[0];
  let maxValue = values[1];
  let minValue = values[2];
  let autoStop = values[4];
  let delay = values[3];
  let sleepOptimization = binSniperOpts.sleepOptimize();

  Sniper(itemName, maxValue, minValue, delay, autoStop, sleepOptimization);
  // ChatLib.chat("Sniper Started.");
}