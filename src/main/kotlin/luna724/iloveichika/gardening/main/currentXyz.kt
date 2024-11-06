package luna724.iloveichika.gardening.main

import luna724.iloveichika.gardening.Gardening
import net.minecraft.client.entity.EntityPlayerSP
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow


fun areDoublesApproximatelyEqual(double1: Double, double2: Double, tolerance: Double): Boolean {
    return abs(double1 - double2) <= tolerance
}
fun roundDown(value: Double, decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return floor(value * factor) / factor
}

fun getCurrentXYZ(decimalPlace: Int = 2): List<Double>? {
    val player: EntityPlayerSP? = Gardening.mc.thePlayer
    val posX = player?.posX
    val posY = player?.posY
    val posZ = player?.posZ
    if (posX == null || posY == null || posZ == null) return null


    val posXRounded = roundDown(posX, decimalPlace)
    val posYRounded = roundDown(posY, decimalPlace)
    val posZRounded = roundDown(posZ, decimalPlace)

    return listOf(posXRounded, posYRounded, posZRounded)
}

fun convertSessionOptToXYZLists(sessionOpts: Map<String, sessionOpt>): List<List<Double>>  {
    val xyzLists: MutableList<List<Double>> = mutableListOf()
    for ((k, v: sessionOpt) in sessionOpts) {
        xyzLists.add(v.coordinates)
    }
    return xyzLists
}

fun checkXYZisIn(xyzLists: List<List<Double>>, targetXYZ: List<Double>, tolerance: Double = 0.25, forceTolerance: Boolean = false): Pair<Boolean, Int> {
    // TODO: 値を設定から取得
    var toleranceXZ = 0.25
    if (forceTolerance) toleranceXZ = tolerance

    val X = targetXYZ[0]
    val Y = targetXYZ[1]
    val Z = targetXYZ[2]

    for ((i, xyz) in xyzLists.withIndex()) {
        val x = xyz[0]
        val y = xyz[1]
        val z = xyz[2]


        if ( // TODO: X, Z の誤差は設定で変更できるようにする
            areDoublesApproximatelyEqual(X, x, toleranceXZ) &&
            areDoublesApproximatelyEqual(Y, y, tolerance) &&
            areDoublesApproximatelyEqual(Z, z, toleranceXZ)
        ) {
            return Pair(true, i)
        }
    }
    return Pair(false, -1)
}

/**
 * XYZ同士を指定した誤差を許容しながら取得する
 */
fun compareXYZ(xyz1: List<Double>, xyz2: List<Double>,
               tolerance: Double = 0.5, toleranceY: Double = 0.01): Boolean {
    val x1 = xyz1[0]; val y1 = xyz1[1]; val z1 = xyz1[2]
    val x2 = xyz2[0]; val y2 = xyz2[1]; val z2 = xyz2[2]

    if (
        areDoublesApproximatelyEqual(x1, x2, tolerance) &&
        areDoublesApproximatelyEqual(y1, y2, toleranceY) &&
        areDoublesApproximatelyEqual(z1, z2, tolerance)
    ) return true
    return false
}