import {
  @ButtonProperty,
  @CheckboxProperty,
  Color,
  @ColorProperty,
  @PercentSliderProperty,
  @SelectorProperty,
  @SwitchProperty,
  @TextProperty,
  @Vigilant,
  @SliderProperty
} from '../Vigilance/index';

@Vigilant("LunaClient", "§zLunaClient", {
  getCategoryComparator: () => (a,b) => {
    const categories = ["General", "Dungeons", "Hider", "LunaAPI Utils"];
    return categories.indexOf(a.name) - categories.indexOf(b.name);
  },
})

class Settings {

  // General
  @SwitchProperty({
    name: "World load notifier",
    description: "Sends message to chat when world loaded",
    category: "General"
  })
  WorldLoadNotice = true

  @TextProperty({
    name: "World load notifier text",
    description: "What message will be sent when world loaded",
    category: "General",
  })
  WorldLoadNoticeText = "Hello, world!"

  // Auction Message Tweaks
  @SwitchProperty({
    name: "Auction Purchased Message Optimizer",
    description: "Optimize AH Purchased message",
    category: "General"
  })
  AhPurchasedOptimizer = true

  @SwitchProperty({
    name: "Remove BIN sleeping notice",
    description: "Remove an §c\"This BIN sale is still in its grace period!\"§f message",
    category: "Hider"
  })
  RemoveBINSleepingNotice = true

  // Blood room notice
  @SwitchProperty({
    name: "Blood room full Notice",
    description: "Show title and text when Blood rooms full",
    category: "Dungeons"
  })
  NoticeBloodRoomsFull = false

  // Blood room full message
  @SwitchProperty({
    name: "Send message when Blood rooms full",
    description: "send message when blood room is full",
    category: "Dungeons"
  })
  sendMessageWhenBloodRoomsFull = true

  @TextProperty({
    name: "Blood room full Message",
    description: "",
    category: "Dungeons"
  })
  MessageWhenBloodRoomsFull = "zzz.."

  // Silent Watcher
  @SwitchProperty({
    name: "Silent Watcher",
    description: "hide message by The Watcher in dungeons",
    category: "Hider"
  })
  SilentWatcher = true

  // Same info hider
  @SwitchProperty({
    name: "Some info hider",
    description: "hide some dungeon information text",
    category: "Hider"
  })
  dungeonInfoHider = true

  // Compact Skymall
  @SwitchProperty({
    name: "Compact SkyMall message",
    description: "Optimize Sky Mall New Buff message",
    category: "General"
  })
  CompactSkyMallMessage = true

  // Treasure Talisman Tracker
  @SwitchProperty({
    name: "Treasure Talisman Flip tracker",
    description: "Track Treasure Talisman flip (/calcProfit treas)",
    category: "LunaAPI Utils"
  })
  TreasureTalismanFlipTracker = false

  // Mute external XP earned message
  @SwitchProperty({
    name: "External XP earned message hider",
    description: "Hide \"You earned 402 GEXP + 1,206 Event EXP from playing SkyBlock!\" message",
    category: "Hider"
  })
  externalXpMessageHider = true

  // f-ing event
  @SwitchProperty({
    name: "[DEPRECATED]: Event Message hider",
    description: "Hide hypixel Event (not sb) leveled-up message",
    category: "Hider"
  })
  eventLevelUpHider = true

  // don expresso
  @SwitchProperty({
    name: "Remove DON Expresso's talking",
    description: "bc Don are the worst SB player.",
    category: "Hider"
  })
  donExpressoMute = true

  // Automatically back to island
  @SwitchProperty({
    name: "Automatically back to island",
    description: "§l§cit is bannable?",
    category: "General"
  })
  autoBacktoIsland = false
  
  constructor() {
    this.initialize(this)
    this.setCategoryDescription("General", "Hello, world!")
    this.addDependency("World load notifier text", "World load notifier")
    this.addDependency("Blood room full Message", "Send message when Blood rooms full")
    this.addDependency("Remove BIN sleeping notice", "Auction Purchased Message Optimizer")
  }
}

export default new Settings()