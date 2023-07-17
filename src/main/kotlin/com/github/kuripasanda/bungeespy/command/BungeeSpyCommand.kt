package com.github.kuripasanda.bungeespy.command

import com.github.kuripasanda.bungeespy.BungeeSpy
import com.github.kuripasanda.bungeespy.data.PlayerData
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class BungeeSpyCommand: Command("bungeespy"), TabExecutor {

    private val plugin = BungeeSpy.plugin
    private val playerDataHandler = BungeeSpy.playerDataHandler
    private val message = BungeeSpy.message

    override fun execute(sender: CommandSender?, args: Array<out String>?) {

        val player = sender as ProxiedPlayer

        if (!sender.hasPermission("bungeespy.admin")) {
            message.sendMessage(sender, true, "&cあなたにはこのコマンドを実行する権限がありません")
            return
        }

        if (args == null || args.isEmpty()) {
            return
        }

        when (args[0]) {
            "chat" -> {
                if (args.size < 2) {
                    message.sendMessage(player, true, "&c引数が足りません")
                    return
                }
                when (args[1]) {
                    "list" -> {
                        var spyingServersMessage = "&8<None>"
                        if (playerDataHandler.hasPlayerData(player.uniqueId)
                            && playerDataHandler.getPlayerData(player.uniqueId)!!.monitorServers.isNotEmpty()) {
                            spyingServersMessage = "&b${playerDataHandler.getSpyServers(player.uniqueId).joinToString("&8, &b")}"
                        }
                        message.sendMessage(player, true, "&6&l&n監視中のサーバー一覧")
                        message.sendMessage(player, false, spyingServersMessage)
                    }
                    "add" -> {
                        if (args.size < 3) {
                            message.sendMessage(player, true, "&cサーバー名を指定してください")
                            return
                        }
                        if (args[2] == "all") {
                            val registerServers = plugin.proxy.servers.keys.toMutableList()
                            registerServers.removeAll(playerDataHandler.getSpyServers(player.uniqueId).toSet())
                            if (registerServers.isEmpty()) {
                                message.sendMessage(player, true, "&c既に全てのサーバーチャットが監視対象に登録されています")
                                return
                            }
                            playerDataHandler.addSpyServers(player.uniqueId, registerServers.toList())
                            message.sendMessage(player, true, "&a全てのサーバーのチャットを監視対象に追加しました")
                        }else {
                            if (playerDataHandler.getSpyServers(player.uniqueId).contains(args[2])) {
                                message.sendMessage(player, true, "&c既に&e${args[2]}&cは登録されています")
                                return
                            }
                            playerDataHandler.addSpyServer(player.uniqueId, args[2])
                            message.sendMessage(player, true, "&b${args[2]}&aのチャットを監視対象に追加しました")
                        }
                    }
                    "remove" -> {
                        if (args.size < 3) {
                            message.sendMessage(player, true, "&cサーバー名を指定してください")
                            return
                        }
                        if (args[2] == "all") {
                            if (playerDataHandler.getSpyServers(player.uniqueId).isEmpty()) {
                                message.sendMessage(player, true, "&c既に全てのサーバーチャットが監視対象から外されています")
                                return
                            }
                            playerDataHandler.removeSpyServers(player.uniqueId, playerDataHandler.getSpyServers(player.uniqueId))
                            message.sendMessage(player, true, "&a全てのサーバーのチャットを監視対象から外しました")
                        }else {
                            if (!playerDataHandler.getSpyServers(player.uniqueId).contains(args[2])) {
                                message.sendMessage(player, true, "&e${args[2]}&cは登録されていません")
                                return
                            }
                            playerDataHandler.removeSpyServer(player.uniqueId, args[2])
                            message.sendMessage(player, true, "&b${args[2]}&aのチャットを監視対象から外しました")
                        }
                    }
                }
            }
            "moveserver" -> {
                if (args.size < 2) {
                    message.sendMessage(player, true, "&c/bungeespy moveserver toggle を実行してください")
                    return
                }
                if (args[1] == "toggle") {
                    if (playerDataHandler.monitorServerMovePlayerUUIDs.contains(player.uniqueId)) {
                        playerDataHandler.monitorServerMovePlayerUUIDs.remove(player.uniqueId)
                        message.sendMessage(player, true, "&aサーバー間移動の監視を無効にしました")
                    }else {
                        playerDataHandler.monitorServerMovePlayerUUIDs.add(player.uniqueId)
                        message.sendMessage(player, true, "&aサーバー間移動の監視を有効にしました")
                    }
                }else {
                    message.sendMessage(player, true, "&cそのようなコマンドはありません")
                }
            }
            else -> message.sendMessage(player, true, "&cそのようなコマンドはありません")
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        val tab = mutableListOf<String>()
        val player = sender as ProxiedPlayer

        if (args == null || args.size <= 1) {
            tab.add("chat")
            tab.add("moveserver")
            return tab
        }

        if (args.size == 2) {
            when (args[0]) {
                "chat" -> {
                    if ("list".startsWith(args[1])) tab.add("list")
                    if ("add".startsWith(args[1])) tab.add("add")
                    if ("remove".startsWith(args[1])) tab.add("remove")
                }
                "moveserver" -> if ("toggle".startsWith(args[1])) tab.add("toggle")
            }
        }

        if (args.size == 3) {
            if (args[0] == "chat") {
                if (args[1] == "add") {
                    val notAddedServers = plugin.proxy.servers.keys.toMutableList()
                    notAddedServers.removeAll(playerDataHandler.getSpyServers(player.uniqueId).toSet())
                    if (notAddedServers.isEmpty()) tab.add("[none]")
                    if (notAddedServers.isNotEmpty()) tab.add("all")
                    for (serverName in notAddedServers) if (serverName.startsWith(args[2])) tab.add(serverName)
                }
                if (args[1] == "remove") {
                    if (playerDataHandler.hasPlayerData(player.uniqueId)) {
                        val spyServers = playerDataHandler.getSpyServers(player.uniqueId)
                        if (spyServers.isEmpty()) tab.add("[none]")
                        if (spyServers.isNotEmpty()) tab.add("all")
                        for (serverName in spyServers) if (serverName.startsWith(args[2])) tab.add(serverName)
                    }
                }
            }
        }

        return tab
    }

}