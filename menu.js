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

const Version = JSON.parse(FileLib.read(
  "LunaClient", "metadata.json"
)).version;

@Vigilant("LunaClient", `§zLunaClient (v${Version})`, {
  getCategoryComparator: () => (a,b) => {
    const categories = ["General", "Dungeons", "Hider", "BinSniper", "LunaAPI Utils", "Garden Utils"];
    return categories.indexOf(a.name) - categories.indexOf(b.name);
  },
})
class Settings {

  // General
  @SwitchProperty({
    name: "World load notifier",
    description: "Sends message to chat when world loaded",
    category: "General",
    subcategory: "Chat Tweaks"
  })
  WorldLoadNotice = true;

  @TextProperty({
    name: "World load notifier text",
    description: "What message will be sent when world loaded",
    category: "General",
    subcategory: "Chat Tweaks"
  })
  WorldLoadNoticeText = "Hello, world!";

  // Auction Message Tweaks
  @SwitchProperty({
    name: "Auction Purchased Message Optimizer",
    description: "Optimize AH Purchased message",
    category: "General",
    subcategory: "Chat Tweaks"
  })
  AhPurchasedOptimizer = true;

  @SwitchProperty({
    name: "Remove BIN sleeping notice",
    description: "Remove an §c\"This BIN sale is still in its grace period!\"§f message",
    category: "Hider"
  })
  RemoveBINSleepingNotice = true;

  // Blood room notice
  @SwitchProperty({
    name: "Blood room full Notice",
    description: "Show title and text when Blood rooms full",
    category: "Dungeons",
    subcategory: "Blood room"
  })
  NoticeBloodRoomsFull = false;

  // Blood room full message
  @SwitchProperty({
    name: "Send message when Blood rooms full",
    description: "send message when blood room is full",
    category: "Dungeons",
    subcategory: "Blood room"
  })
  sendMessageWhenBloodRoomsFull = true;

  @TextProperty({
    name: "Blood room full Message",
    description: "",
    category: "Dungeons",
    subcategory: "Blood room"
  })
  MessageWhenBloodRoomsFull = "zzz..";

  // Silent Watcher
  @SwitchProperty({
    name: "Silent Watcher",
    description: "hide message by The Watcher in dungeons",
    category: "Hider"
  })
  SilentWatcher = true;

  // Same info hider
  @SwitchProperty({
    name: "Some info hider",
    description: "hide some dungeon information text",
    category: "Hider"
  })
  dungeonInfoHider = true;

  // Compact Skymall
  @SwitchProperty({
    name: "Compact SkyMall message",
    description: "Optimize Sky Mall New Buff message",
    category: "General",
    subcategory: "Chat Tweaks"
  })
  CompactSkyMallMessage = true;

  // Treasure Talisman Tracker
  @SwitchProperty({
    name: "Treasure Talisman Flip tracker",
    description: "Track Treasure Talisman flip (/calcProfit treas)",
    category: "LunaAPI utils",
    subcategory: "/calcProfit"
  })
  TreasureTalismanFlipTracker = false;

  // Mute external XP earned message
  @SwitchProperty({
    name: "External XP earned message hider",
    description: "Hide \"You earned 402 GEXP + 1,206 Event EXP from playing SkyBlock!\" message",
    category: "Hider"
  })
  externalXpMessageHider = true;

  // f-ing event
  @SwitchProperty({
    name: "[DEPRECATED]: Event Message hider",
    description: "Hide hypixel Event (not sb) leveled-up message",
    category: "Hider"
  })
  eventLevelUpHider = true;

  // don expresso
  @SwitchProperty({
    name: "Remove DON Expresso's talking",
    description: "bc Don are the worst SB player.",
    category: "Hider"
  })
  donExpressoMute = true;

  // Automatically back to island
  @SwitchProperty({
    name: "Automatically back to island",
    description: "§l§cit is bannable?",
    category: "General",
    subcategory: "Macro"
  })
  autoBacktoIsland = false;

  // Hide death message
  @SwitchProperty({
    name: "Hide player death Message",
    description: "",
    category: "Hider"
  })
  hideDeathMessage = false;

  @SwitchProperty({
    name: "Don't hide player death message while Dungeons",
    description: "Kuudra aren't supported",
    category: "Hider"
  })
  hideDeathMessageExceptDungeon = false;

  // BinSniper
  @TextProperty({
    name: "Target Item",
    description: "Target item name, use for /ahs {Target_item}",
    category: "BinSniper"
  })
  BinSniperTargetItem = "God Potion";

  @TextProperty({
    name: "Max value",
    description: "Snipe target max value (snipe < this)",
    category: "BinSniper"
  })
  BinSniperMaxValue = "1.85M";
  
  @TextProperty({
    name: "Min value",
    description: "Snipe target min value (this < snipe)",
    category: "BinSniper"
  })
  BinSniperMinValue = "850K";

  @SwitchProperty({
    name: "Stop at (purse < Min value)",
    description: "",
    category: "BinSniper"
  })
  BinSniperAutoStop = true;

  @SwitchProperty({
    name: "[beta]: Optimize Snipe function for Bin sleeping",
    description: "this may occur Memory leak / broken BinSniper",
    category: "BinSniper"
  })
  BinSniperSleepOptimization = false;

  @TextProperty({
    name: "[int] delay",
    description: "Set sniper delay, This value should be integer.",
    category: "BinSniper"
  })
  BinSniperDelay = "450"

  // // BIN Snipe Helper
  // @SwitchProperty({
  //   name: "[WARN]: BIN Snipe Helper",
  //   description: "utils for Sleeping BIN",
  //   category: "General"
  // })
  // binSnipeHelper = false;

  // @TextProperty({
  //   name: "BIN Snipe Helper disable caps",
  //   description: "BIN Snipe Helper will be disabled when (BIN prices > this)",
  //   category: "General"
  // })
  // binSnipeHelperCap = "100M";

  // Flip track helper
  @SwitchProperty({
    name: "Flip track helper",
    description: "track your flip with Lunapy bot",
    category: "LunaAPI utils",
    subcategory: "Flip track helper"
  })
  LunaAPIFlipTrackHelper = false;

  @SwitchProperty({
    name: "Track Bazaar",
    description: "track your BZ insta-buy,buy/sell order",
    category: "LunaAPI utils",
    subcategory: "Flip track helper"
  })
  LunaAPIFlipTrackHelperBazaar = false;

  @SwitchProperty({
    name: "Check when buy/sell order Started",
    description: "",
    category: "LunaAPI utils",
    subcategory: "Flip track helper"
  })
  LunaAPIFlipTrackHelperCheckWhenOrder = false;

  @SwitchProperty({
    name: "Check when you Claimed buy/sell order",
    description: "",
    category: "LunaAPI utils",
    subcategory: "Flip track helper"
  })
  LunaAPIFlipTrackHelperCheckWhenOrderClaimed = true;

  @SwitchProperty({
    name: "Check when you Cancelled buy order",
    description: "§cSell order aren't Supported!",
    category: "LunaAPI utils",
    subcategory: "Flip track helper"
  })
  LunaAPIFlipTrackHelperCheckWhenOrderCancelled = true;

  @SwitchProperty({
    name: "Auto-rejoin Skyblock",
    description: "when you got kicked to lobby, Auto-rejoin Skyblock",
    category: "General",
    subcategory: "Macro"
  })
  AutoRejoinSkyblock = true;

  @SwitchProperty({
    name: "Notice \"Kicked!\" to chat",
    description: "When you got kicked, send message to Party chat",
    category: "General",
    subcategory: "Macro"
  })
  AutoRejoinSkyblockKickedMessage = true;

  @TextProperty({
    name: "\"Kicked!\" message",
    description: "",
    category: "General",
    subcategory: "Macro"
  })
  AutoRejoinSkyblockKickedMessageText = "Skyblock hates me!!!";

  @SwitchProperty({
    name: "Notice \"joined!\" to chat",
    description: "when you rejoined, send message to Party chat",
    category: "General",
    subcategory: "Macro"
  })
  AutoRejoinSkyblockEndedMessage = true;

  @TextProperty({
    name: "\"joined!\" message",
    description: "",
    category: "General",
    subcategory: "Macro"
  })
  AutoRejoinSkyblockEndedMessageText = "!warp";

  @SelectorProperty({
    name: "AutoMove",
    description: "/lc_move to start. 100% Client Side",
    category: "Garden Util",
    subcategory: "Macro",
    options: ["LeftMove", "RightMove", "ForwardMove", "Only hoverClick"] 
  })
  GardenAutoMove = 0;

  @SwitchProperty({
    name: "HoverClick",
    description: "/lc_move to start. 100% Client side",
    category: "Garden Util",
    subcategory: "Macro",
  })
  GardenAutoMoveIncludesClick = true;

  @SwitchProperty({
    name: "AutoMove Safe mode",
    description: "activate some ways to automatically disable AutoMove",
    category: "Garden Util",
    subcategory: "Macro"
  })
  GardenAutoMoveSafeMode = true;

  @SwitchProperty({
    name: "Hide Sacks message",
    description: "While in garden",
    category: "Garden Util",
    subcategory: "Hide"
  })
  GardenHideSacksMessage = true;

  constructor() {
    this.initialize(this)
    this.setCategoryDescription("General", "Hello, world!")
    this.setCategoryDescription("BinSniper", "/lc_binsniper to Start Snipe!")
    this.addDependency("World load notifier text", "World load notifier")
    this.addDependency("Blood room full Message", "Send message when Blood rooms full")
    this.addDependency("Remove BIN sleeping notice", "Auction Purchased Message Optimizer")
    this.addDependency("Don't hide player death message while Dungeons", "Hide player death Message")
    // this.addDependency("BIN Snipe Helper disable caps", "[WARN]: BIN Snipe Helper")
    this.addDependency("Check when you Cancelled buy order", "Track Bazaar")
    this.addDependency("Check when you Claimed buy/sell order", "Track Bazaar")
    this.addDependency("Check when buy/sell order Started", "Track Bazaar")
    this.addDependency("Track Bazaar", "Flip track helper")
    this.addDependency("Notice \"joined!\" to chat", "Auto-rejoin Skyblock")
    this.addDependency("\"joined!\" message", "Notice \"joined!\" to chat")
  }
}

export default new Settings()