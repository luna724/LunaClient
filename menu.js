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

@Vigilant("LunaClient", `§dLunaClient (v${Version})`, {
  getCategoryComparator: () => (a,b) => {
    const categories = ["General", "Dungeons", "Hider", "Farming"];
    return categories.indexOf(a.name) - categories.indexOf(b.name);
  },
})
class Settings {
  // // LunaAPI Utils Start

  // @SwitchProperty({
  //   name: "Treasure Talisman Flip tracker",
  //   description: "Track Treasure Talisman flip (/calcProfit treas)",
  //   category: "LunaAPI utils",
  //   subcategory: "/calcProfit"
  // })
  // TreasureTalismanFlipTracker = false;
  
  // // Flip track helper
  // @SwitchProperty({
  //   name: "Flip track helper",
  //   description: "track your flip with Lunapy bot",
  //   category: "LunaAPI utils",
  //   subcategory: "Flip track helper"
  // })
  // LunaAPIFlipTrackHelper = false;

  // @SwitchProperty({
  //   name: "Track Bazaar",
  //   description: "track your BZ insta-buy,buy/sell order",
  //   category: "LunaAPI utils",
  //   subcategory: "Flip track helper"
  // })
  // LunaAPIFlipTrackHelperBazaar = false;

  // @SwitchProperty({
  //   name: "Check when buy/sell order Started",
  //   description: "",
  //   category: "LunaAPI utils",
  //   subcategory: "Flip track helper"
  // })
  // LunaAPIFlipTrackHelperCheckWhenOrder = false;

  // @SwitchProperty({
  //   name: "Check when you Claimed buy/sell order",
  //   description: "",
  //   category: "LunaAPI utils",
  //   subcategory: "Flip track helper"
  // })
  // LunaAPIFlipTrackHelperCheckWhenOrderClaimed = true;

  // @SwitchProperty({
  //   name: "Check when you Cancelled buy order",
  //   description: "§cSell order aren't Supported!",
  //   category: "LunaAPI utils",
  //   subcategory: "Flip track helper"
  // })
  // LunaAPIFlipTrackHelperCheckWhenOrderCancelled = true;

  // @SwitchProperty({
  //   name: "Always track your any item",
  //   description: "",
  //   category: "LunaAPI utils",
  //   subcategory: "Flip track helper"
  // })
  // LunaAPIFlipTrackAlwaysTrue = false;

  // /* LunaAPI Utils End*/ 
  

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
    description: "",
    category: "General",
    subcategory: "Macro"
  })
  autoBacktoIsland = false;

  @SelectorProperty({
    name: "IslandType",
    description: "Automatically back island type",
    category: "General",
    subcategory: "Macro",
    options: ["/is", "/garden"]
  })
  autoBackIslandType = 0;

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
  AutoRejoinSkyblockKickedMessageText = "kicked";

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
  AutoRejoinSkyblockEndedMessageText = "joined";

  @SwitchProperty({
    name: "Auto-back Trapper",
    description: "Auto-back to Trapper when trapper mobs died.",
    category: "Farming",
    subcategory: "Farming Islands"
  })
  FarmingAutoBackTrapper = false;

  @SwitchProperty({
    name: "Hide Sacks message",
    description: "While in garden",
    category: "Farming",
    subcategory: "Garden"
  })
  GardenHideSacksMessage = true;

  @SwitchProperty({
    name: "Auto-warp Desert Settlement",
    description: "if Trevors locate animal in \"Oasis\" or \"Desert Settlement\", run /warp desert",
    category: "Farming",
    subcategory: "Farming Islands"
  })
  FarmingAutoWarpDesert = false;

  @SliderProperty({
    name: "Auto-warp/back delay",
    description: "ms",
    category: "Farming",
    subcategory: "Farming Islands",
    min: 50,
    max: 2000
  })
  FarmingAutoWarpDelay = 450;

  constructor() {
    this.initialize(this)
    this.setCategoryDescription("General", "Hello, world!")
    this.addDependency("World load notifier text", "World load notifier")
    this.addDependency("Blood room full Message", "Send message when Blood rooms full")
    this.addDependency("Remove BIN sleeping notice", "Auction Purchased Message Optimizer")
    this.addDependency("Don't hide player death message while Dungeons", "Hide player death Message")

    /* LunaAPI Utils Start */
    // this.addDependency("Check when you Cancelled buy order", "Track Bazaar")
    // this.addDependency("Check when you Claimed buy/sell order", "Track Bazaar")
    // this.addDependency("Check when buy/sell order Started", "Track Bazaar")
    // this.addDependency("Track Bazaar", "Flip track helper")
    /* LunaAPI Utils End */

    this.addDependency("Notice \"joined!\" to chat", "Auto-rejoin Skyblock")
    this.addDependency("\"joined!\" message", "Notice \"joined!\" to chat")
  }
}

export default new Settings()