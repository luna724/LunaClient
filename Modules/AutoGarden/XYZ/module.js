import { header, xyzCollection } from "../Identifier";

export function getXYZ() {
  // 現在のプレイヤーの XYZ をそのまま返す
  const x = Player?.getX();
  const y = Player?.getY();
  const z = Player?.getZ();

  if (typeof x != "number" || typeof y != "number" || typeof z != "number") {
    console.error(`Player Locations Invalid! (${x}, ${y}, ${z})`);
    ChatLib.chat("Player XYZs Invalid!");
  }
  return [x, y, z];
}

export function getResizedXYZ(XYZ=null) {
  // セーブ用に1桁にされた XYZ を返す
  // 引数 XYZ が null の場合、getXYZ() の値が XYZ に代入される
  if (XYZ == null) { 
    XYZ = getXYZ();
  }

  XYZ[0] = Math.floor(XYZ[0]);
  XYZ[1] = Math.floor(XYZ[1]);
  XYZ[2] = Math.floor(XYZ[2]);
  return XYZ;
}

export function getRotation() {
  // 現在のプレイヤーの Yaw / Pitch を返す
  const yaw = Player?.getYaw();
  const pitch = Player?.getPitch();

  if (typeof yaw != "number" || typeof pitch != "number") {
    console.error(`Player Rotations Invalid! (${yaw}, ${pitch})`);
    ChatLib.chat("Player Rotations Invalid!");
  }
  return [yaw, pitch];
}

export function getResizedRotation(Rotation=null) {
  // セーブ用に 少数1位までにされた Rotation を返す
  // 引数 Rotation が null の場合、getRotation() の値が Rotation に代入される
  if (Rotation == null) {
    Rotation = getRotation();
  }
  
  Rotation[0] = Math.floor(Rotation[0] * 10) / 10;
  Rotation[1] = Math.floor(Rotation[1] * 10) / 10;
  return Rotation;
}

export function checkDirection(direction) {
  // setxyz <trigger> に渡される Direction を正則化する
  const availableChar = "rlfb";
  
  direction = direction.toLowerCase();
  if (direction == "reset") {
    return "reset";
  }
  
  let regex = new RegExp(`^[${availableChar}]+$`);
  if (!regex.test(direction)) {
    ChatLib.chat(header + "\"direction\" can only contain l or r or f or b or \"reset\". but got " + direction);
    return "reset";
  }
  return direction;
}

export function getConfig() {
  // 現在のプリセットの XYZ コンフィグを返す
  const rawJson = JSON.parse(
    FileLib.read("LunaClient", "autogarden.json")
  );

  if (!rawJson || typeof rawJson !== "object") {
    console.error("Invalid config object.");
    return {};
  }
  return rawJson;
}

export function saveConfig(newcfg) {
  const backup = getConfig();
  console.log(
    `[module.js:saveConfig]: Backup: ${JSON.stringify(backup)}`
  );

  FileLib.write("LunaClient", "autogarden.json", JSON.stringify(newcfg))
}

export function getNewKey() {
  const characters = 'abcdefghijklmnopqrstuvwxyz0123456789';
    
  // characters に応じた内容をランダム生成
  function generateRandomString() {
      const length = Math.floor(Math.random() * 3) + 4; // 4～6文字のランダムな長さ
      let result = '';
      for (let i = 0; i < length; i++) {
          result += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      return result;
  }

  let takenKey = Object.keys(getConfig());
  let newString = generateRandomString();

  while (takenKey.includes(newString)) {
    newString = generateRandomString();
  }
  return newString;
}