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
    const categories = ["AutoGarden"];
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

  constructor() {
    this.initialize(this)
  }
}

export const autoGardenSetting = new autoGardenSettings();