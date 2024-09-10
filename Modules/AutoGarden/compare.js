

export function compareNumberwithRange(base, compare, range) {
  // -50, -49.7, 0.5
  // -> -50, -50.2, -49.2

  // 50, 49.7, 0.5
  // -> 50, 49.2, 50.2
  const compareMin = compare - range;
  const compareMax = compare + range;

  // -50 >= -50.2 and -50 <= -49.2 => True
  // 50 >= 49.2 and 50 <= 50.2 => True
  if (base >= compareMin && base <= compareMax) {
    return true;
  }
  return false;
}


/*
  @Param (XYZ1, XYZ2, strict?)
  
  XYZ1 === XYZ2 のチェックを行う
  strict == True の場合、完全一致の場合にのみ true を返す
  false の場合、XYZ1 をベース(Int[3])。 XYZ2 を Float[3] として
  XYZ2 の誤差 0.5 以内に XYZ1 が当てはまれば、 true を返す
  また、この関数は IndexError に対するハンドリングを持っていない
*/

export function compareXYZ(XYZ1, XYZ2, strict=false) {
  const X1 = XYZ1[0];
  const X2 = XYZ2[0];
  const Y1 = XYZ1[1];
  const Y2 = XYZ2[1];
  const ignoreY = (Y1 === -1 || Y2 === -1);
  const Z1 = XYZ1[2];
  const Z2 = XYZ2[2];

  
  if ( strict &&
    X1 === X2 && (Y1 === Y2 || ignoreY) && Z1 === Z2
  ) { return true; }

  if ( !strict &&
    compareNumberwithRange(X1, X2, 0.5) &&
    (compareNumberwithRange(Y1, Y2, 0.5) || ignoreY) && 
    compareNumberwithRange(Z1, Z2, 0.5)
  ) {
    return true;
  }

  return false;
}