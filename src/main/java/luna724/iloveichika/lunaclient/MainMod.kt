package luna724.iloveichika.lunaclient

import net.minecraftforge.fml.common.event.FMLInitializationEvent

@net.minecraftforge.fml.common.Mod(modid = MainMod.MODID, name = MainMod.NAME, version = MainMod.VERSION)
class MainMod {

    companion object {
        const val MODID = "lunaclient"
        const val NAME = "LunaClient"
        const val VERSION = "2.0pre"
    }

    @net.minecraftforge.fml.common.Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        // 初期化処理などを書く場所
        println("$NAME mod is loaded!")
    }
}
