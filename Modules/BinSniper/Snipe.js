import binSniperOpts from "./shared";
import { resizeAuctionStyle } from "./resize_coin";

function openAuction(itemName) { // /ahs {item_name} でオークションを開く
  // GUI名 から正しく開いたかを検出し、正しい場合動作を再開する
  ChatLib.command(`ahs ${itemName}`);
}

function purchaseItem(itemNBT, itemIndex, isSleeping=false) {
  ChatLib.chat(`purchaseItem called! itemIndex: ${itemIndex}`)
}

function checkItemPrices(itemName, maxValue, minValue) {
  // アイテム価格帯を取得
  // 価格帯が許容範囲内なら購入 (purchaseItem)
  let itemIndex = [
    11, 12, 13, 14, 15, 16,
    19, 20, 21, 22, 23, 24,
    27, 28, 29, 30, 31, 32,
    35, 36, 37, 38, 39, 40
  ];
  let items = Player?.getContainer()?.getItems()?.filter((item, index) => itemIndex.includes(index));
  let MostLowItem = null;

  for (const [index, item] of items.entries()) {
    // アイテム名がマッチしないならパス
    if (!item.getName().toLowerCase().includes(itemName.toLowerCase())) continue;
    
    // 価格を取得
    let Lores = item.getLore();
    let itemPriceInfo = Lores.slice().reverse().find(lore => lore.startsWith("Buy it now:"));
    let itemPrice = null;
    let remainTimes = Lores.slice().reverse().find(lore => lore.startsWith("Can buy in:") || lore.startsWith("Ends in:"));
    
    // can buy in.. でかつ MostLowItem が null ならそれを追加
    if (remainTimes.startsWith("Can buy in:") && MostLowItem == null) {
      MostLowItem = [index, item];
    }
    if (!remainTimes.startsWith("Can buy in:")) {
      itemPrice = resizeAuctionStyle(itemPriceInfo);
      // 許容範囲なら買う
      if (minValue <= itemPrice <= maxValue) {
        purchaseItem(item.getNBT(), index);
        break;
      }
    }
    else continue
    
    // 価格を取得し、許容範囲内なら購入、外ならMostLowItem をスパム
    itemPrice = resizeAuctionStyle(itemPriceInfo);
    if (minValue <= itemPrice <= maxValue) {
      purchaseItem(item.getNBT(), index);
      break;
    }
    else if (maxValue < itemPrice && MostLowItem != null) {
      // MostLowItem をスパム
      purchaseItem(MostLowItem[1].getNBT(), MostLowItem[0], true)
      break;
    }
  }
}

function sniper(i, max, min, delay, autostop) {
  if (Player?.getContainer()?.getName()?.startsWith("Auctions:")) {
    let status = checkItemPrices(itemName, max, min)
    if (!status) { // 購入に失敗 -> 見つかるまでオークションをロールする

    }
  }
}


export default function Snipe(itemName, max, min, delay, autoStop, sleepOptimize) {
  let containerNameException = [
    "BIN Auction View", "Confirm Purchase"
  ]
  register("step", () => {
    // Binsniperが有効でない、Auctions が開かれていない
    if (!binSniperOpts.getStatus()) return
    if (containerNameException.includes(Player?.getContainer()?.getName()) || Player?.getContainer()?.getName()?.startsWith("Auctions:")) {}
    else return;

    // オークションが開かれてるなら
    sniper(itemName, max, min, delay, autoStop);
  
  }).setFps(5)


}