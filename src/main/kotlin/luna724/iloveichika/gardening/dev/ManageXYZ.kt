package luna724.iloveichika.gardening.dev

import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import luna724.iloveichika.gardening.main.ManageXYZ
import luna724.iloveichika.gardening.util.SessionOpt
import luna724.iloveichika.gardening.util.addSessionOpt
import luna724.iloveichika.gardening.main.checkDirectionsCorrectly
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import java.util.*

class ManageXYZ {
    private val header: String = "§7[§7ManageXYZ§7]§f7: "
    private val normalManager: ManageXYZ = ManageXYZ()
    /**コマンド実行失敗*/
    private fun sendError(e: String) {
        mc.thePlayer.addChatComponentMessage(
            ChatComponentText(
                header+e
            )
        )
    }
    fun setXYZ(
        sender: ICommandSender, args: Array<String>
    ) {
        if (args.size != 9) {
            sendError("Arguments incorrectly")
            return
        }
        val x = args[1].toDoubleOrNull()?: return
        val y = args[2].toDoubleOrNull()?: return
        val z = args[3].toDoubleOrNull()?: return
        val yaw = args[4].toDoubleOrNull()?: return
        val pitch = args[5].toDoubleOrNull()?: return
        val direction = checkDirectionsCorrectly(args[6])?: return
        val changePitch = args[7].lowercase(Locale.getDefault()).toBooleanStrictOrNull()?: false
        val key = args[8]
        val xyz = listOf(x,y,z)
        val rotation = listOf(yaw,pitch)

        addSessionOpt(
            key, SessionOpt(
                xyz, rotation, direction, changePitch
            )
        )
        val baseMsg = ChatComponentText("§aSaved as §l${key}§r. §7(XYZ: ${xyz}, Rotation: ${rotation}, Direction: ${direction}) ")
        sender.addChatMessage(
            normalManager.makeRemove(baseMsg, key)
        )
    }
}