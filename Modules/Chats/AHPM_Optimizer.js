import Settings from "../../menu"

export default function AHPM_Optimizer_main(message, event) {
  if (!Settings.AhPurchasedOptimizer) return;
  // 非表示にするキーワード
  let hideMessage = [
    "Putting coins in escrow...",
    "Processing purchase...",
    "Visit the Auction House to collect your item!",
    "Putting item in escrow...",
    "Setting up the auction...",
    "Checking escrow for recent transaction..."
  ]

  if (hideMessage.includes(message.removeFormatting())) {
    event.setCanceled(true); // メッセージを非表示にする
  }
  // This BIN sale is still in its grace period!
  if (Settings.RemoveBINSleepingNotice) {
    if (message.removeFormatting() == "This BIN sale is still in its grace period!") {
      event.setCanceled(true)
    }
  }

  if (message.removeFormatting() == "There was an error with the auction house! (AUCTION_EXPIRED_OR_NOT_FOUND)") {
    // NOT FOUND のエラー
    event.setCanceled(true);
    ChatLib.chat("§cThat auctions §l§4Expired §r§cor §l§4Not Found§r§c!")
  }

  if (typeof message !== 'undefined' && message !== null) {
    let pattern = /^You claimed (.*) from (.*)'s auction!$/;
    let match = message.match(pattern);
    if (match) {
        event.setCanceled(true);
        // match2 プレイヤー名を処理
        playerName = match[2]
        let pn = playerName.split("]")[playerName.split("]").length - 1];
        message.startsWith()
        if (playerName.startsWith("[VIP")) {
          playerName = `§a${pn}`
        } else if (playerName.startsWith("[MVP")) {
          playerName = `§b${pn}`
        } else if (playerName.startsWith("[YO")) {
          playerName = `§c${pn}`
        } else if (playerName.startsWith("[")) {
          playerName = `§4${pn}`
        } else {
          playerName = ` §7${pn}`
        }

        ChatLib.chat(`§eClaimed §6${match[1]} §efrom${playerName}§e's auction!`);
    }
  }
}