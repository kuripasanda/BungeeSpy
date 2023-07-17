package com.github.kuripasanda.bungeespy.listener

import com.github.kuripasanda.bungeespy.BungeeSpy
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class PlayerDisconnectListener: Listener {

    private val playerDataHandler = BungeeSpy.playerDataHandler

    @EventHandler
    fun onPlayerDisconnect(event: PlayerDisconnectEvent) {

        val player = event.player

        playerDataHandler.removePlayerData(player.uniqueId)

    }

}