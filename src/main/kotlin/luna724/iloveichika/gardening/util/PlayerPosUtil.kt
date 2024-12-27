package luna724.iloveichika.gardening.util

import luna724.iloveichika.lunaclient.LunaClient.Companion.config
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import net.minecraft.client.entity.EntityPlayerSP
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow

/**
 * プレイヤーのXYZ, Yaw, Pitch に関連することに対する細かな機能を提供するクラス
 * Gardening でのみインスタンス化が許可される
 */
class PlayerPosUtil {
    companion object {
        var player: EntityPlayerSP = mc.thePlayer ?: throw NullPointerException("Player is null!")
    }

    init {

    }

    /**
     * Double 同士の誤差を測定し、その誤差が許容範囲内であるかを返す
     */
    private fun compareDoubleWithTolerance(
        x1: Double, x2: Double, tolerance: Double
    ): Boolean {
        return abs(x1 - x2) <= tolerance
    }

    /**
     * Double を特定の小数点まで繰り上げする
     */
    private fun roundDown(value: Double, decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces.toDouble())
        return floor(value * factor) / factor
    }

    /**
     * プレイヤーの現在地の座標を取得する
     *
     * decimalPlace はその地点で少数切り捨てを行うことを指す
     * @return: List<X, Y, Z>
     */
    fun getPlayerPosition(decimalPlace: Int = 2): List<Double> {
        val x = roundDown(player.posX, decimalPlace)
        val y = roundDown(player.posY, decimalPlace)
        val z = roundDown(player.posZ, decimalPlace)

        return listOf(x, y, z)
    }

    /**
     * プレイヤーの現在の視点を取得する
     *
     * decimalPlace はその地点で少数切り捨てを行うことを指す
     * @return: List<Yaw, Pitch>
     */
    fun getPlayerRotation(decimalPlaces: Int = 1): List<Double> {
        val yaw = roundDown(player.rotationYaw.toDouble(), decimalPlaces)
        val pitch = roundDown(player.rotationPitch.toDouble(), decimalPlaces)

        return listOf(yaw, pitch)
    }

    /**
     * XYZのリスト (List<List<Double>>) の中に targetXYZ (List<Double>) が含まれているかどうかを
     * 誤差付きで検出する
     *
     * @return: 一致したかどうか、最初に一致した XYZリストのインデックスをペアで返す
     * 一致しなかった場合は False, -1 のペアを返す
     */
    fun XYZisIn(
        listXYZ: List<List<Double>>, targetXYZ: List<Double>, forceToleranceXZ: Double? = null, forceToleranceY: Double? = null
    ): Pair<Boolean, Int> {
        val toleranceXZ: Double = forceToleranceXZ ?: config.autoGardenCategory.autoGarden.xzTolerance.toDouble()
        val toleranceY: Double = forceToleranceY ?: config.autoGardenCategory.autoGarden.yTolerance.toDouble()

        // ループを用いり最初の一致を検索
        for ((i, xyz) in listXYZ.withIndex()) {
            // ループ内比較には compareXYZ 関数を用いる
            if (compareXYZ(xyz, targetXYZ, toleranceXZ, toleranceY)) {
                return Pair(true, i)
            }
        }
        return Pair(false, -1)
    }

    /**
     * XYZ と XYZ を誤差付きで比較する
     *
     * @return: 一致したかどうか
     */
    fun compareXYZ(
        xyz1: List<Double>, xyz2: List<Double>, forceToleranceXZ: Double? = null, forceToleranceY: Double? = null
    ): Boolean {
        val toleranceXZ: Double = forceToleranceXZ ?: config.autoGardenCategory.autoGarden.xzTolerance.toDouble()
        val toleranceY: Double = forceToleranceY ?: config.autoGardenCategory.autoGarden.yTolerance.toDouble()

        return compareDoubleWithTolerance(xyz1[0], xyz2[0], toleranceXZ) &&
                compareDoubleWithTolerance(xyz1[1], xyz2[1], toleranceY) &&
                compareDoubleWithTolerance(xyz1[2], xyz2[2], toleranceXZ)
    }
}