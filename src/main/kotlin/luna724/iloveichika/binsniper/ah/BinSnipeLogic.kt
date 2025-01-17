package luna724.iloveichika.binsniper.ah;

import luna724.iloveichika.binsniper.BinSniper.Companion.binSniperKey
import luna724.iloveichika.lunaclient.LunaClient.Companion.mc
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.input.Keyboard

class BinSnipeLogic {
    @SubscribeEvent
    fun onKey(event: GuiScreenEvent.KeyboardInputEvent) {
        /**
         * ビンスナキーが押されたことを検出する
         */
        // チェスト画面のみ (AH menu)
        if (event.gui is GuiChest) {
            // なぜかは知らんけどシフトが押されたら止める
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode)) {
                // TODO: stopsnipe
                return
            }
            else if ( // 有効時に ESC キーが押されたら停止
                Keyboard.isKeyDown(1) &&
                binSniperKey.isPressed
            )
        }
    }
}
