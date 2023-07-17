package com.github.kuripasanda.bungeespy.listener

import com.github.kuripasanda.bungeespy.BungeeSpy
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class ServerSwitchListener: Listener {

    private val plugin = BungeeSpy.plugin
    private val playerDataHandler = BungeeSpy.playerDataHandler
    private val message = BungeeSpy.message

    @EventHandler
    fun onServerSwitch(event: ServerSwitchEvent) {

        val player = event.player
        val fromServer = event.from

        if (fromServer == null) {
            for (uuid in playerDataHandler.monitorServerMovePlayerUUIDs) {
                val monitoringPlayer = plugin.proxy.getPlayer(uuid) ?: return
                message.sendMessage(monitoringPlayer, false,
                    "&6[Monitor] [&3<Join>&b → ${player.server.info.name}&6] &2${player.name}")
            }
        }else {
            for (uuid in playerDataHandler.monitorServerMovePlayerUUIDs) {
                val monitoringPlayer = plugin.proxy.getPlayer(uuid) ?: return
                message.sendMessage(monitoringPlayer, false,
                    "&6[Monitor] [&b${fromServer.name} → ${player.server.info.name}&6] &2${player.name}")
            }
        }

    }

}