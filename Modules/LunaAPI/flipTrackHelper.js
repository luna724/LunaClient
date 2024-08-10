import { request } from "axios";
import Settings from "../../menu";

function sendPostData(command, k, s, v) {
  request({
    url: "http://localhost:9876/flip_tracker",
    method: "POST",
    body: {
      command: command,
      key: k,
      subst: s,
      value: v
    },
    json: true
  })
  .then(response =>   {
    // 成功
    console.info("Success: " + JSON.stringify(response.data));

    let msg = response.data["message"];
    msg = msg.replace("OK.", "§aOK.").replace("(with tracking)", "§o§7(with tracking)§r§6").replace("Response: ", "");
    ChatLib.chat("[§dLunaClient§f]: [§n§dLunaAPI§f§r]: "+`${msg}`);
  })
  .catch(error => {
    if (error.isAxiosError) {
      ChatLib.chat("[§dLC§f]: §cHTTP Error: " + error.code + ": " + error.response.data);
      } else {
          ChatLib.chat("[§dLC§f]: §cError: " + error.message);
      }
    });
}

register("command", (k, s, v="") => {
  // ユーザーによる呼び出しなので厳しく検査
  let available_s = ["-", "+"];
  if (available_s.includes(s)) {
    sendPostData("/", k, s, v)
  }
  else {
    subst = s[0]
    if (!available_s.includes(s)) {
      subst = "+";
    }
    v = s.slice(1);
    sendPostData("/", k, s, v)
  }
}).setName("coin")

register("command", (subst, price) => {
  // sendCheckMessage() からの呼び出しのみを想定
  sendPostData("/", "ah", subst, price)
}).setName("lunaclient_flip_tracker_track")

function sendCheckMessage(price, substType, custom_message="[§abuy§f/§csell§f/§eorder§f]?") {
  let message = new Message(
    `§f[§dLunaClient§f]: Track this ${custom_message}  `,
    new TextComponent("§a[Yes] ")
      .setClick("run_command", `/lunaclient_flip_tracker_track ${substType} ${price}`)
      .setHover("show_text", `Click to send "/coin ah ${price}" to LunaAPI!`)
  )
  ChatLib.chat(message);
}

// register("chat") -> Moved to /Chat.js ln:142
export function flipTrackHelper(message) {
  //! BZ-Insta: [Bazaar] Bought 1x Cobblestone for 4.8 coins!
  //! BZ-Insta: [Bazaar] Sold 1x Cobblestone for 1.8 coins!

  //! AH-Insta: You purchased Treasure Talisman for 800,000 coins!
  //! AH-Sell-Ended: You collected 8,524,890 coins from selling Wand of Atonement to [MVP] JaayViibesTV in an auction!

  //! BZ-Offer: [Bazaar] Buy Order Setup! 243x Revenant Viscera for 20,993,232 coins.
  //! BZ-Offer: [Bazaar] Sell Offer Setup! 1x Sludge Juice for 3,076 coins.
  //! BZ-Offer-Ended: [Bazaar] Claimed 76x Revenant Viscera worth 6,565,784 coins bought for 86,392 each!
  //! BZ-Offer-Ended: [Bazaar] Claimed 1,068,790 coins from selling 12x Null Ovoid at 90,079 each!
  //! BZ-Offer-Cancel: [Bazaar] Cancelled! Refunded 14,427,447 coins from cancelling Buy Order!
  // BZ-Offer-Cancel: [Bazaar] Cancelled! Refunded 1x Water Orb from cancelling Sell Offer!

  let trigger = [
    "You purchased", "You collected", "[Bazaar]"
  ];
  let ok = true;

  if (ok) {
    // BZ-Insta (buy)
    if (message.removeFormatting().startsWith("[Bazaar] Bought")) {
      let Pattern = /^\[Bazaar\] Bought (.*)x (.*) for (.*) coins!$/;
      let match = message.removeFormatting().match(Pattern);

      if (match && Settings.LunaAPIFlipTrackHelperBazaar) {
        sendCheckMessage(match[3], "-",
          `§ainsta-buy? §f[§a${match[1]}§7x §e${match[2]}§f] (§6-${match[3]}§f)`
        );
      }
    }
    // BZ-Offer-Started (buy)
    else if (message.removeFormatting().startsWith("[Bazaar] Buy Order Setup!")) {
      let Pattern = /^\[Bazaar\] Buy Order Setup! (.*)x (.*) for (.*) coins.$/;
      let match = message.removeFormatting().match(Pattern);

      if (match && Settings.LunaAPIFlipTrackHelperBazaar && Settings.LunaAPIFlipTrackHelperCheckWhenOrder) {
        sendCheckMessage(match[2], "-",
          `§aorder? §f[§a${match[1]}§7x §e${match[2]}§f] (§6-${match[3]}§f)`
        );
      }
    }
    // BZ-Offer-Claimed (buy/sell)
    else if (message.removeFormatting().startsWith("[Bazaar] Claimed")) {
      // BZ-Offer-Claimed (sell) 
      if (message.removeFormatting().includes("from selling")) {
        let Pattern = /^\[Bazaar\] Claimed (.*) coins from selling (.*)x (.*) at [0-9,]+ each!$/;
        let match = message.removeFormatting().match(Pattern);

        if (match && Settings.LunaAPIFlipTrackHelperBazaar && Settings.LunaAPIFlipTrackHelperCheckWhenOrderClaimed) {
          sendCheckMessage(match[1], "+",
            `§aClaimed order? §f[§a${match[2]}§7x §e${match[3]}§f] (§6+${match[1]}§f)`
          )
        }
      }
      else if (message.removeFormatting().includes("bought for")) {
        // buy
        let Pattern = /^\[Bazaar\] Claimed (.*)x (.*) worth (.*) coins bought for [0-9,]+ each!$/;
        let match = message.removeFormatting().match(Pattern);

        if (match && Settings.LunaAPIFlipTrackHelperBazaar && Settings.LunaAPIFlipTrackHelperCheckWhenOrderClaimed) {
          sendCheckMessage(match[3], "-",
            `§aClaimed order? §f[§a${match[1]}§7x §e${match[2]}§f] (§6-${match[3]}§f)`
          );
        }
      }
    }
    // BZ-Offer-Cancelled (buy)
    else if (message.removeFormatting().startsWith("[Bazaar] Cancelled!")) {
      if (message.removeFormatting().endsWith("from cancelling Buy Order!")) {
        // buy
        let Pattern = /^\[Bazaar\] Cancelled! Refunded (.*) coins from cancelling Buy Order!$/;
        let match = message.removeFormatting().match(Pattern);
        
        if (match && Settings.LunaAPIFlipTrackHelperBazaar && Settings.LunaAPIFlipTrackHelperCheckWhenOrderCancelled) {
          sendCheckMessage(match[1], "+",
            `§cCancelled order? (§6+${match[1]}§f)`
          );
        }
      }
    }
    // BZ-Insta (sell)
    else if (message.removeFormatting().startsWith("[Bazaar] Sold")) {
      let Pattern = /^\[Bazaar\] Sold (.*)x (.*) for (.*) coins!$/;
      let match = message.removeFormatting().match(Pattern);

      if (match && Settings.LunaAPIFlipTrackHelperBazaar) {
        sendCheckMessage(match[3], "+",
          `§cinsta-sell? §f[§a${match[1]}§7x §e${match[2]}§f] (§6+${match[3]}§f)`
        );
      }
    }
    // BZ-Offer-Started (sell)
    else if (message.removeFormatting().startsWith("[Bazaar] Sell Offer Setup!")) {
      let Pattern = /^\[Bazaar\] Sell Offer Setup! (.*)x (.*) for (.*) coins.$/;
      let match = message.removeFormatting().match(Pattern);
      
      if (match && Settings.LunaAPIFlipTrackHelperBazaar && Settings.LunaAPIFlipTrackHelperCheckWhenOrder) {
        sendCheckMessage(match[3], "-",
          `§coffer? §f[§a${match[1]}§7x §e${match[2]}§f] (§6-${match[3]}§f)`
        );
      }
    }


    // AH-Purchased
    else if (message.removeFormatting().startsWith("You purchased") && message.removeFormatting().endsWith("coins!")) {
      let Pattern = /^You purchased (.*) for (.*) coins!$/;
      let match = message.removeFormatting().match(Pattern);

      if (match) {
        sendCheckMessage(match[2], "-",
          `§cauction? §f[§e${match[1]}§f] (§6-${match[2]}§f)`
        );
      }
    }
    // AH-Selled
    else if (message.removeFormatting().startsWith("You collected") && message.removeFormatting().endsWith("in an auction!")) {
      let Pattern = /^You collected (.*) coins from selling (.*) to .* in an auction!$/;
      let match = message.removeFormatting().match(Pattern);

      if (match) {
        sendCheckMessage(match[1], "+",
          `§cSold auction? §f[§e${match[2]}§f] (§6+${match[1]}§f)`
        )
      }
    }
  }
};