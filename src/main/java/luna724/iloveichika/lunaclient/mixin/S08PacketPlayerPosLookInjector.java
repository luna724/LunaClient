package luna724.iloveichika.lunaclient.mixin;

/**
 * S08PacketPlayerPosLook: サーバーによるテレポートパケットを処理する関数
 * mixinを用いりInjectし、異常なテレポートが検出されたら AutoGardenを停止する
 */
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class S08PacketPlayerPosLookInjector {

    /**
     * `handlePlayerPosLook` メソッドにインジェクト。
     * サーバーから送信された S08PacketPlayerPosLook をフックし、独自の処理を加えます。
     *
     * @param packet パケット（テレポート情報）
     * @param ci CallbackInfo：メソッド実行フローを制御できます
     */
    @Inject(method = "handlePlayerPosLook", at = @At("HEAD"), cancellable = true)
    private void onHandlePlayerPosLook(S08PacketPlayerPosLook packet, CallbackInfo ci) {
        // パケットのデータを取得
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        float yaw = packet.getYaw();
        float pitch = packet.getPitch();

        // パケット内容をデバッグ用に表示
        System.out.println("Intercepted S08PacketPlayerPosLook: x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + ", pitch=" + pitch);
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText("Intercepted S08PacketPlayerPosLook: x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + ", pitch=" + pitch)
        );

        // 必要があればここで処理をキャンセルする
        // 例: テレポートをキャンセルしたい場合は以下のコードをアンコメント
        // ci.cancel();
    }
}