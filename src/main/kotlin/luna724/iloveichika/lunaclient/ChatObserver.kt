package luna724.iloveichika.lunaclient

import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
class ChatObserver {

    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText
        // チャットメッセージを処理するためのコード
        println("Received chat message: $message")
    }
}
