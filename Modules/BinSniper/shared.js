import Settings from "../../menu";
import {
  txt2coin
} from "./resize_coin";

// スナイプ中に設定の変更は行わない
// 設定保存変数
class binSniperOpts {
  constructor() {
    this.targetItem = Settings.BinSniperTargetItem;
    this.autoStop = Settings.BinSniperAutoStop;
    this.sleepOptimization = Settings.BinSniperSleepOptimization;
    
    this.delay = parseInt(Settings.BinSniperDelay);
    this.maxValue = txt2coin(Settings.BinSniperMaxValue);
    this.minValue = txt2coin(Settings.BinSniperMinValue);

    this.lunaClientBinSniperActive = false;
  }

  getStatus() { // アクティブ化どうかを返す
    return this.lunaClientBinSniperActive;
  }
  
  updateStatus(newStatus) { // ステータスを更新する
    this.lunaClientBinSniperActive = newStatus;  
  }

  getValues() { // 必要な値をリストで返す。 Sniperの初期化時に使用
    let values = [
      this.targetItem, this.maxValue, this.minValue,
      this.delay, this.autoStop
    ];
    return values;
  }

  sleepOptimize() { // self.sleepOptimization の値を返す
    return this.sleepOptimization;  
  }

  updateValues () { // self. の値をすべて更新。 スタート時に使う
    this.targetItem = Settings.BinSniperTargetItem;
    this.autoStop = Settings.BinSniperAutoStop;
    this.sleepOptimization = Settings.BinSniperSleepOptimization;
    
    this.delay = parseInt(Settings.BinSniperDelay);
    this.maxValue = txt2coin(Settings.BinSniperMaxValue);
    this.minValue = txt2coin(Settings.BinSniperMinValue);
  }
}
export default new binSniperOpts();