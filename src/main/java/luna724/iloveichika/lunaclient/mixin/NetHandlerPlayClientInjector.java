package luna724.iloveichika.lunaclient.mixin;

import luna724.iloveichika.gardening.antiantimacro.TeleportationPacketHandler;
import luna724.iloveichika.lunaclient.LunaClient;
import luna724.iloveichika.lunaclient.cheating.Blink;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * S08PacketPlayerPosLook: サーバーによるテレポートパケットを処理する関数
 * mixinを用いりInjectし、異常なテレポートが検出されたら AutoGardenを停止する
 */
@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientInjector {
    @Unique
    private static final TeleportationPacketHandler lunaClient$callback;

    @Unique
    private static final Blink lunaClient$blink;

    static {
        lunaClient$callback = new TeleportationPacketHandler();
        lunaClient$blink = LunaClient.blink;
    }

    //
    @Inject(method = "handlePlayerPosLook", at = @At("HEAD"), cancellable = true)
    private void onHandlePlayerPosLook(S08PacketPlayerPosLook packet, CallbackInfo ci) {
        // 登録済みのコールバックがある場合は実行
        boolean result = lunaClient$callback.onPacketReceived(packet, ci);
        if (result) {
            ci.cancel();
        }
    }

    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    private void onAddToSendQueue(Packet packet, CallbackInfo ci) {
        if (lunaClient$blink.isProcessing()) { return; }
        if (packet instanceof C03PacketPlayer) {
            if (!lunaClient$blink.isEnable()) {
                return;
            }
            ci.cancel();
            lunaClient$blink.queuePacket(packet);
        }
    }
}