import { request } from "axios";
import Settings from "../menu"

// Pythonへデータを送信
function sendPostRequest(coin) {
  request({
      url: "http://localhost:9876/receive_data", // ここにサーバーのURLを指定
      method: "POST",
      body: {
          mode: "treas",
          value: coin
      },
      json: true
  })
  .then(response => {
      // 成功した場合の処理
      console.info("Success: " + JSON.stringify(response.data));
  })
  .catch(error => {
      // エラー処理
      if (error.isAxiosError) {
          ChatLib.chat("[§dLC§f]: §cHTTP Error: " + error.code + ": " + error.response.data);
      } else {
          ChatLib.chat("[§dLC§f]: §cError: " + error.message);
      }
  });
}

// 定義
register("chat", (coin, event) => {
  if (Settings.TreasureTalismanFlipTracker) {
        ChatLib.chat("§f[§dLunaClient§f]: §epurchased §9Treasure talisman §efor §g" + coin)
        event.setCanceled(true)
        // データを Python に送信
        sendPostRequest(coin)
  }
}).setCriteria("You purchased Treasure Talisman for ${coin} coins!")

register("command", (coin) => {
  if (Settings.TreasureTalismanFlipTracker) {
    ChatLib.chat("§f[§dLunaClient§f]: §epurchased §9Treasure talisman §efor §g" + coin)
    sendPostRequest(coin)
  }
}).setName(
  "treas_flip"
).setAliases("lctf")