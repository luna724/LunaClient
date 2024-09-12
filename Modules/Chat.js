import Settings from "../menu"

import AHPM_Optimizer_main from "./Chats/AHPM_Optimizer"
import hideDeathMessage from "./Chats/hideDeathMessage";
import { flipTrackHelper } from "./LunaAPI/flipTrackHelper";

import { checkInWorld } from "../../GriffinOwO/utils/Location";

function noticeBloodRoomsFull() {
  if (!Settings.NoticeBloodRoomsFull) return;
  // クライアントメッセージ
  ChatLib.chat("§l[§dLC§f]: §cBlood room is enough now.")
  World.playSound("note.pling", 2, 1)
  Client.showTitle("§l§cBlood Room §fis enough now!", "", 5, 50, 5)
  
  if (!Settings.MessageWhenBloodRoomsFull) return;
  // チャット
  ChatLib.command(`pc ${Settings.MessageWhenBloodRoomsFull}`)
}

register("chat", (chat, event) => {
  let index = "§f[§dLunaClient§f]:"
  // BloodRooms
  if (chat.removeFormatting() == "[BOSS] The Watcher: That will be enough for now.") {
    noticeBloodRoomsFull();
  }
    // Remove The Watcher text
  if (chat.removeFormatting().startsWith("[BOSS]: The Watcher:")) {
    if (Settings.SilentWatcher) {
      event.setCanceled(true)
    }
  }
    // Some Message deleter
  if (Settings.dungeonInfoHider) {
    let targetMessage = [
      "RIGHT CLICK on a WITHER door to open it. This key can only be used to open 1 door!",
      "RIGHT CLICK on the BLOOD DOOR to open it. This key can only be used to open 1 door!",
      "A shiver runs down your spine...",
      "There are no players available to revive right now!",
      "You do not have the key for this door!"
    ];
    let targetMessageStarts = [
      "[Tank] ", "[Healer] ", "[Berserker] ", "[Archer] ", "[Mage] ",
      "Your Ultimate is currently on cooldown for ",
      "[NPC] Mort: "
    ];
    if (targetMessage.includes(chat.removeFormatting())) {
      event.setCanceled(true);
    }
    for (let trig of targetMessageStarts) {
      if (chat.removeFormatting().startsWith(trig)) {
        event.setCanceled(true);
        break;
      }
    }

  // AHPM_Optimizer
  if (Settings.AhPurchasedOptimizer) {
    AHPM_Optimizer_main(chat, event)
  }

  // Compact SkyMall
  if (Settings.CompactSkyMallMessage) {
    let skymall_delete = [
      "You can disable this messaging by toggling Sky Mall in your /hotm!",
      "New day! Your Sky Mall buff changed!"
    ]
    if (skymall_delete.includes(chat.removeFormatting())) {
      event.setCanceled(true)
    }
    const SMPattern = /^New buff: (.*)/;
    const SMMatch = chat.match(SMPattern);
    if (SMMatch) {
      event.setCanceled(true);
      let buff = SMMatch[1];
      let buff_message = "";

      if (buff == "Gain +100 ⸕ Mining Speed.") {
        buff_message = "Gain §a+100 §6⸕ Mining Speed.";
      } 
      else if (buff == "Gain 5x Titanium drops") {
        buff_message = "Gain §a5x §9Titanium §fdrops."
      }
      else if (buff == "Gain +50 ☘ Mining Fortune.") {
        buff_message = "Gain §a+50 §6☘ Mining Fortune";
      }
      else if (buff == "Gain +15% more Powder while mining.") {
        buff_message = "Gain §a+15% §fmore §dPowder §fwhile mining.";
      }
      else if (buff == "Reduce Pickaxe Ability cooldown by 20%.") {
        buff_message = "Reduce Pickaxe Ability cooldown by §a20%§f.";
      }
      else if (buff == "10x chance to find Golden and Diamond Goblins.") {
        buff_message = "§a10x §fchance to find §6Golden §fand §bDiamond §fGoblins.";
      }
      else {
        ChatLib.chat(`${index} §cUnsupported SkyMall Buff: ${buff}`);
        buff_message = buff
      }

      ChatLib.chat(`§d[§bNew §2Sky Mall §bbuff§f§d]§f: ${buff_message}`);
    }
  };

  // Mute external XP earned message
  let ch = chat.removeFormatting()
  if (Settings.externalXpMessageHider) {
    if (ch.startsWith("You earned") && ch.endsWith("from playing SkyBlock!")) {
      event.setCanceled(true);
    }
  }

  // donExpresso Hider
  if (Settings.donExpressoMute) {
    if (chat.removeFormatting().startsWith("[NPC] Don Expresso: ")) {
      event.setCanceled(true)
    }
  }
  
  // Autoback to island
  if (Settings.autoBacktoIsland) {
    if (chat.removeFormatting() == "You cannot join SkyBlock from here!" || chat.removeFormatting() == "You were spawned in Limbo.") {
      ChatLib.command("l prototype");
    }
	if (chat.removeFormatting() == "Unknown command. Type \"help\" for help. ('is')") {
	  event.setCanceled(true);
    ChatLib.command("play sb");
	}
  }

  // Hide death message
  if (Settings.hideDeathMessage) {
    hideDeathMessage(chat, event);
  }
  

  // flipTrackHelper
  if (Settings.LunaAPIFlipTrackHelper) {
    flipTrackHelper(chat)
  }

  // Auto-rejoin Skyblock
  if (Settings.AutoRejoinSkyblock) {
    if (chat.removeFormatting() == "You were kicked while joining that server!") {
      new Thread(() => {
        Thread.sleep(444);
        if (Settings.AutoRejoinSkyblockKickedMessage) {
          ChatLib.command(`pc ${Settings.AutoRejoinSkyblockKickedMessageText}`);
        }
        Thread.sleep(5000); // 5秒待機
        ChatLib.chat(`${index} Waiting for 60s..`);
        Thread.sleep(65000) // 60秒待機
        ChatLib.chat(`${index} Trying to rejoin`);
        Thread.sleep(1000);
        ChatLib.command("play sb");

        Thread.sleep(500);
        if (Settings.AutoRejoinSkyblockEndedMessage) {
          ChatLib.command(`pc ${Settings.AutoRejoinSkyblockEndedMessageText}`);
        }
      }).start();
    }
  }

  // Garden Hide Sacks message
  if (Settings.GardenHideSacksMessage) {
    if (checkInWorld("Garden")) {
      if (chat.removeFormatting().startsWith("[Sacks] ")) {
        event.setCanceled(true)
      }
    }
  }

  // Auto-back trapper
  if (Settings.FarmingAutoBackTrapper) {
    if (chat.removeFormatting() == "Return to the Trapper soon to get a new animal to hunt!") {
	  new Thread(() => {
	  Thread.sleep(Settings.FarmingAutoWarpDelay);
      ChatLib.command("warp trapper");
	  }
	  ).start()
	  }
  }

  if (Settings.FarmingAutoWarpDesert) {
    if (chat.removeFormatting().startsWith("[NPC] Trevor:")) {
      if (chat.removeFormatting().endsWith("animal near the Desert Settlement.") || chat.removeFormatting().endsWith("animal near the Oasis.")) {
        new Thread(() => {
          Thread.sleep(Settings.FarmingAutoWarpDelay);
          ChatLib.command("warp desert");
        }).start()
      }
    }
  }

  // Internal deleter (can't toggle)
  let internal_blacklist = [
    "Blacklisted modifications are a bannable offense!",
    "Warping...",
    "Command Failed: This command is on cooldown! Try again in about a second!",
    "[MVP+] Mizuki_25ji joined the lobby!",
    " >>> [MVP++] Mafuyu_25zi joined the lobby! <<<",
    "[MVP+] Mafuyu_25zi joined the lobby!",
    "This block is already occupied!",
    "  ➤ You have reached your Hype limit! Add Hype to Prototype Lobby minigames by right-clicking with the Hype Diamond!",
    "You are still radiating with Generosity!"
  ];
  if (internal_blacklist.includes(chat)) {
    event.setCanceled(true)
  }

}}).setCriteria("${chat}");