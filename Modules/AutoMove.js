import Settings from "../menu";

class AutoMoveOpts {
  isEnabled = false;

  keyForward = null;
  keyBackward = null;
  keyRight = null;
  keyLeft = null;
  keyLeftClick = null;
  dontMove = false;
  targetKey = null;
  clickActive = false;

  antiAntiMacro = null;
  autoStop = 1800000; // (30minute)
}
const AutoMoveOpt = new AutoMoveOpts()

const Registered = register("tick", () => {
  if (!AutoMoveOpt.isEnabled) return;
  let targetKey = AutoMoveOpt.targetKey;

  if (targetKey == "key.left") {

  }
  else if (targetKey == "key.right") {
    Client.getMinecraft()?.gameSettings.keyBindRight.pressed = true;
  }
  else if (targetKey == "key.forward") {
    Client.getMinecraft().gameSettings.keyBindForward.pressed = true;
  }

  if (AutoMoveOpt.clickActive) {
    Client.getMinecraft()?.gameSettings.eyBindAttack.pressed = true;
  }
})

export function AutoMove() {
  // 開始
  // AutoMoveOpt を更新する
  let targetKey = ["key.left", "key.right", "key.forward", ""][Settings.GardenAutoMove];
  let binds = Client.getKeyBindFromDescription(targetKey);

  if (binds == null) {
    AutoMoveOpt.dontMove = true;
  }
  else {
    AutoMoveOpt.dontMove = false;
  }

  let clickBind = Client.getKeyBindFromDescription("key.attack");
  AutoMoveOpt.keyLeftClick = clickBind;
  AutoMoveOpt.keyBackward = Client.getKeyBindFromDescription("key.backward");
  AutoMoveOpt.keyForward = Client.getKeyBindFromDescription("key.forward");
  AutoMoveOpt.keyLeft = Client.getKeyBindFromDescription("key.left");
  AutoMoveOpt.keyRight = Client.getKeyBindFromDescription("key.right");
  AutoMoveOpt.targetKey = targetKey;

  AutoMoveOpt.antiAntiMacro = Settings.GardenAutoMoveSafeMode;
  AutoMoveOpt.clickActive = Settings.GardenAutoMoveIncludesClick;

  let Mode = null;
  

  // 定義
  Registered.register();
}

export function StopAutoMove() {
  // 非定義
  Registered.unregister();
}


register("command", () => {
  ChatLib.chat("[§dLunaClient§f]: §7/lc_move has deprecated.")
  ChatLib.chat("[§dLunaClient§f]: §7instead, use this MOD")
  ChatLib.chat("[§dLunaClient§f]: [§aLC-AutoMove§f]: §f[§9https://github.com/luna724/LC-AutoMove§f]")
  
  ChatLib.chat("[§dLunaClient§f]: §7AutoMove Enabled");
  ChatLib.chat("[§dLunaClient§f]: §7AutoMove Disabled.");
  // AutoMoveOpt.isEnabled = !AutoMoveOpt.isEnabled

  // if (AutoMoveOpt.isEnabled) {
  //   ChatLib.chat("[§dLunaClient§f]: §7AutoMove Enabled");
    
  //   //AutoMove();
  // }
  // else {
  //   ChatLib.chat("[§dLunaClient§f]: §7AutoMove Disabled.");
  //   StopAutoMove();
  // }

}).setName("lc_move")