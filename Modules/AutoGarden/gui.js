import { compareNumberwithRange } from "./compare";
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
} from '../../../Vigilance/index';

@Vigilant("LunaClientAutoGarden", "§dLunaClient §f/ §2AutoGarden", {
  getCategoryComparator: () => (a,b) => {
    const categories = ["AutoGarden", "Pests"];
    return categories.indexOf(a.name) - categories.indexOf(b.name);
  },
})
class autoGardenSettings { 
  // antiAntiMacro main
  @SwitchProperty({
    name: "Anti-AntiMacro Enable",
    description: "Enable Anti-AntiMacro when activate AutoGarden",
    category: "AutoGarden",
    subcategory: "Safety"
  })
  enableAntiAntiMacro = true

  // antiAntiMacro triggered()
  @SwitchProperty({
    name: "Auto-Limbo when Anti-AntiMacro Triggered",
    description: "",
    category: "AutoGarden",
    subcategory: "Safety"
  })
  autoDisconnectWhenTriggered = false;

  @TextProperty({
    name: "Auto-Limbo command",
    description: "only can use Client-sided command (lobby to /l)",
    category: "AutoGarden",
    subcategory: "Safety"
  })
  autoDisconnectWhenTriggeredCommand = "/skytils:limbo";
  
  // Chat Converter Engine
  @SwitchProperty({
    name: "convert LC-AutoMove message",
    description: "convert compact style to LC-AutoMove message",
    category: "AutoGarden",
    subcategory: "Spam"
  })
  chatConverterEngine = true;
  
  // Pest
  @SwitchProperty({
    name: "PestTracker",
    description: "Pests related function's dependencies",
    category: "Pests",  
    subcategory: "Main"
  })
  pestTracker = true;

  // warn at nPests
  @SliderProperty({
    name: "Warn at nPests spawned.",
    description: "Warn by sounds. set 0 to Disable",
    category: "Pests",
    subcategory: "QoL",
    min: 0,
    max: 8
  })
  pestAllowed = 4;

  // Stop at nPests reached
  @SliderProperty({
    name: "Stop at nPests spawned.",
    description: "Stops AutoGarden. set 0 to Disable, set -1 to sync with WARN",
    category: "Pests",
    subcategory: "QoL",
    min: -1,
    max: 8
  })
  pestAllowedInAutoStop = -1;

  // Pest Repellent
  // @SwitchProperty({
  //   name: "NEP",
  //   description: "send Warning when Pest Repellents Expired.",
  //   category: "Pests",
  //   subcategory: "Repellent"
  // })
  // warnPestRepellentExpired = true;

  // STOP at Pest repellent expired.
  // @SwitchProperty({
  //   name: "Stop at Repellent Expired.",
  //   description: "Stops AutoGarden",
  //   category: "Pests",
  //   subcategory: "Repellent"
  // })
  // stopPestRepellentExpired = false;

  // Auto-reYUM Repellent
  // @SwitchProperty({
  //   name: "[COMING SOON]: Auto-reYUM Repellent MAX",
  //   description: "Only supporting Pest Repellent MAX",
  //   category: "Pests",
  //   subcategory: "Repellent"
  // })
  // autoBlinkPestRepellentMAX = false;

  // warn When 5-visitors queued
  // @SwitchProperty({
  //   name: "warn when 5-visitors visited",
  //   description: "need 6-visitors? try SkyHanni",
  //   category: "Visitor",
  //   subcategory: "Notify"
  // })
  // warnFiveVisitorsVisited = false;

  // @SwitchProperty({
  //   name: "Auto-serve when 5-visitors visited",
  //   description: "REQUIRED OringoClient / Auto Visitors.\nRecommend Options in docs/oringo.md",
  //   category: "Visitor",
  //   subcategory: "Macro"
  // })
  // autoVisitorwhenFive = false;

  // @SwitchProperty({
  //   name: "Stop when drop dyes.",
  //   description: "Support: Wild Strawberry, Copper, Dung.\nNotification available in SkyHanni",
  //   category: "AutoGarden",
  //   subcategory: "Safety"
  // })
  // stopWhenGotDyes = false;

  // @SwitchProperty({
  //   name: "Auto-spray",
  //   description: "Automatically use Sprayonator, when Sprays Expired.",
  //   category: "AutoGarden",
  //   subcategory: "Macro"
  // })
  // autoSpray = false;

  constructor() {
    this.initialize(this);

    // this.addDependency("Auto-serve when 5-visitors visited", "warn when 5-visitors visited");
    // this.addDependency("Stop at Repellent Expired.", "NEP");
    this.addDependency("Stop at nPests spawned.", "PestTracker");
    this.addDependency("Warn at nPests spawned.", "PestTracker");
  }
}

export const autoGardenSetting = new autoGardenSettings();