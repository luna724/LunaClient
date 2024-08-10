// DO NOT IMPORT ANYTHING

export function resizeAuctionStyle(coin) {
  // return: float
  return parseFloat(coin.replace(",",""));
}


export function txt2coin(txt_coin) {
  x = txt_coin.toLowerCase();
  let trigger = x[x.length - 1]; // Pythonの x[-1] に相当
  let multiplier = 1;

  if (!isNaN(trigger)) { // 文字が数字かどうかをチェック
      multiplier = 1;
  } else {
      x = x.slice(0, -1); // 最後の文字を取り除く

      switch (trigger) {
          case 'k':
              multiplier = 1000;
              break;
          case 'm':
              multiplier = 1000000;
              break;
          case 'b':
              multiplier = 1000000000;
              break;
          default:
              multiplier = 1;
              break;
      }
  }

  // '.' が含まれるかどうかで整数か浮動小数かを決める
  if (x.includes('.')) {
      x = parseFloat(x);
  } else {
      x = parseInt(x);
  }

  return x * multiplier;
};

function under_one(v) {
  if (v < 1) {
    return 0;
  } else {
    return parseInt(v);
  }
}


export default function coin2txt(coin) {
  let v = parseFloat(v);
  let dic = false;

  if (v < 0) {
    dic = true;
    v *= -1; // 整数に変換
  }

  let b = under_one(v / 1000000000);
  let m = under_one(v / 1000000);
  let k = under_one(v / 1000);
  let last = v - k*1000;
  
  m = m - (b*1000);
  k = k - (m*1000);

  let text = "";
  if (b !== 0) {
    text = `${b}`;
    if (m !== 0) {
      text += `.${parseInt(m)}`;
    }
    text += "B";
  }
  else if (m !== 0) {
    text = `${m}`;
    if (k !== 0) {
      text += `.${parseInt(k)}`;
    }
    text += "M";
  }
  else if (k !== 0) {
    text = `${k}`;
    if (last !== 0) {
      text += `.${parseInt(last)}`;
    }
    text += "K";
  }
  else {
    text = `${last}coin`;
  }

  if (dic) {
    text = `-${text}`;
  }
  return text;
};