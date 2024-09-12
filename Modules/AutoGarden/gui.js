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
  // antiAntiMacro triggered()
  @SwitchProperty({
    name: "Auto-Limbo when Anti-AntiMacro Triggered",
    description: "Required Skytils (/limbo",
    category: "AutoGarden",
    subcategory: "Safety"
  })
  autoDisconnectWhenTriggered = false;

  constructor() {
    this.initialize(this)
  }
}

export default new autoGardenSettings();