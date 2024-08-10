import "./resize_coin";

import startSniper from "./sniper";
import binSniperOpts from "./shared";

// Start function
register("command", () => {
  if (binSniperOpts.getStatus()) {
    binSniperOpts.updateStatus(false);
    // 停止関数 または lunaClientBinSniperActive に基づき停止させる
  }
  else {
    // スタート
    binSniperOpts.updateStatus(true);
    binSniperOpts.updateValues();
    startSniper(binSniperOpts.getValues());
  }
}).setName("lc_binsniper").setAliases("bs", "binsniper", "lcbs")