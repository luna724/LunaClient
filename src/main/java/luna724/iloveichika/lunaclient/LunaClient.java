package luna724.iloveichika.lunaclient;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import luna724.iloveichika.lunaclient.ChatObserver;

@Mod(modid = LunaClient.MODID, version = LunaClient.VERSION)
public class LunaClient {
    public static final String MODID = "lunaclient";
    public static final String VERSION = "2.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ChatObserver chatObserver = new ChatObserver();


        MinecraftForge.EVENT_BUS.register(chatObserver);
    }
}