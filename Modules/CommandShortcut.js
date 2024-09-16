register("command", () => {
	ChatLib.command("warp garden");
}).setName("garden").setAliases("gd")

register("command", () => {
	ChatLib.command("warp dh");
}).setName("dh")

register("command", () => {
	ChatLib.chat("§f[§dLunaClient§f]: /lobby has replaced to /l by LunaClient")
	ChatLib.command("l");
}).setName("lobby", true);

register("lc_readme", () => {
	ChatLib.chat("§f[§dLunaClient§f]: README:§7 https://github.com/luna724/LunaClient/blob/main/README.md")
})