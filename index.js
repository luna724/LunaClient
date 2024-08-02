import "./Modules/worldLoad"
import "./Modules/TreasureTalismanFlipTracker"
import "./Modules/Chats/AHPM_Optimizer"
import "./Modules/Chat"

import Settings from "./menu"
import { request } from "axios";

register("command", () => {
  Settings.openGUI()
}).setName('lunaclient').setAliases("lc", "luna")

function sendPostRequest() {
    request({
        url: "http://localhost:9876/receive_data", // ここにサーバーのURLを指定
        method: "POST",
        body: {
            message: "Hello from ChatTriggers!"
        },
        json: true
    })
    .then(response => {
        // 成功した場合の処理
        ChatLib.chat("Success: " + JSON.stringify(response.data));
    })
    .catch(error => {
        // エラー処理
        if (error.isAxiosError) {
            ChatLib.chat("HTTP Error: " + error.code + ": " + error.response.data);
        } else {
            ChatLib.chat("Error: " + error.message);
        }
    });
}

register("command", sendPostRequest).setCommandName("lc_sendpost");