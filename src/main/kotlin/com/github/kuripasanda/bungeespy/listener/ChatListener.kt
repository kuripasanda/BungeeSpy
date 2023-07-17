package com.github.kuripasanda.bungeespy.listener

import com.github.kuripasanda.bungeespy.BungeeSpy
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class ChatListener: Listener {

    private val plugin = BungeeSpy.plugin
    private val playerDataHandler = BungeeSpy.playerDataHandler
    private val message = BungeeSpy.message

    @EventHandler
    fun onChat(event: ChatEvent) {
        val player = event.sender as ProxiedPlayer
        val msg = event.message
        val server = player.server

        val monitoringPlayerUUIDs = playerDataHandler.getPlayersHasMonitorServer(server.info.name)

        for (uuid in monitoringPlayerUUIDs) {
            val player = plugin.proxy.getPlayer(uuid) ?: return

            if (player.server.info.name == server.info.name) return

            message.sendMessage(player, false,
                "&6[Monitor] &7(${server.info.name}&7)&f ${player.name}&a: &f$msg")
        }

    }

}