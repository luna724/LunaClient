import Settings from "../menu"

import AHPM_Optimizer_main from "./Chats/AHPM_Optimizer"

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
      buff = SMMatch[1];
      ChatLib.chat(`§d[§2Sky Mall§f§d]§f: §d[§bNew buff§f§d]§f: §b${buff}`);
    }
  };

  // Mute external XP earned message
  ch = chat.removeFormatting()
  if (Settings.externalXpMessageHider) {
    if (ch.startsWith("You earned") && ch.endsWith("from playing Skyblock!")) {
      event.setCanceled(true);
    }
  }

  // eventLevelUpHider
  if (Settings.eventLevelUpHider) {
    if (chat.startsWith("                       You are now Event Level") || chat.endsWith("Event Silver!")) {
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
    if (chat.removeFormatting() == "You cannot join SkyBlock from here!" || chat.removeFormatting() == "Unknown command. Type \"help\" for help. ('is')") {
      ChatLib.command("l prototype")
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
  ]
  if (internal_blacklist.includes(chat)) {
    event.setCanceled(true)
  }

}}).setCriteria("${chat}");