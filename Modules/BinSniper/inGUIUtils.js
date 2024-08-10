import binSniperOpts from "./shared";
import { resizeAuctionStyle } from "./resize_coin";

function afterQueueItemPurchase(itemIndex, itemInfo, queued) {
  console.log("afterQueueItemPurchase called!, there's args", `itemIndex: ${itemIndex}, itemInfo: ${itemInfo}, queued: ${queued}`);
}

function priceCheck(min, max, current) {
  // 対象が範囲に含まれていたら true を返す
  if (min <= resizeAuctionStyle(current) <= max) {
    return true;
  }
  return false;
}

export function checkItemPrices(itemName, maxValue, minValue) {
  // returns: boolean
  /*
  対照価格内のアイテムがあるかどうか取得、ある場合は全てをリストアップし、
  queueItemPurchase() にアイテム情報を渡す
  */ 

  let patternGettingSleepSeconds = /^Can buy in: (.*)s$/;
  let patternGettingItemPrice = /^/;
  let perfectCheck = false; // tmp

  let itemIndex = [
    11, 12, 13, 14, 15, 16,
    19, 20, 21, 22, 23, 24,
    27, 28, 29, 30, 31, 32,
    35, 36, 37, 38, 39, 40
  ];
  let items = Player?.getContainer()?.getItems()?.filter((item, index) => itemIndex.includes(index));
  // アイテムをリストアップ

  for (const [index, item] of items.entries()) {
    // アイテム名がマッチしないならパス
    if (perfectCheck) {
      if (item.getName() !== itemName) continue;
    }
    else {
      if (!item.getName().toLowerCase().includes(itemName)) continue;
    }

    // アイテムの価格を取得
    let Lores = item.getLore();
    let itemPricing = Lores[Lores.length - 5].removeFormatting()  // 基本 Buy it now: 00,000 coins となる
    if (!itemPricing.startsWith("Buy it now:")) {
      // Buy it now: から始まる列を後ろから処理、取得した値を使用する
      itemPricing = Lores.slice().reverse().find(
        lore => lore.startsWith("Buy it now:")
      );
    }

    let remainTimes = Lores[Lores.length - 3].removeFormatting()  // Ends: .. or Can buy in: ..s
    if (!(remainTimes.startsWith("Ends in:") || remainTimes.startsWith("Can buy in:"))) {
      remainTimes = Lores.slice().reverse().find(
        lore => lore.startsWith("Can buy in:")
      )
    }
    // remainTimes が can buy in.. ならqueue に時間差で追加
    if (remainTimes.startsWith("Can buy in:")) {
      let sleepingTime = remainTimes.match(patternGettingSleepSeconds);
      if (priceCheck(minValue, maxValue, itemPricing)) {
        afterQueueItemPurchase(itemIndex[index], item, sleepingTime);
      }
      break;
    }

    // 即座に購入
    if (priceCheck(minValue, maxValue, itemPricing)) {
      afterQueueItemPurchase(itemIndex[index], item, 0);
    }
  }

};