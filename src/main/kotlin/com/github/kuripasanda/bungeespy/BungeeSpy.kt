package com.github.kuripasanda.bungeespy

import com.github.kuripasanda.bungeespy.command.BungeeSpyCommand
import com.github.kuripasanda.bungeespy.listener.ChatListener
import com.github.kuripasanda.bungeespy.listener.PlayerDisconnectListener
import com.github.kuripasanda.bungeespy.listener.ServerSwitchListener
import com.github.kuripasanda.bungeespy.util.Message
import com.github.kuripasanda.bungeespy.util.PlayerDataHandler
import net.md_5.bungee.api.plugin.Plugin
import kotlin.system.measureTimeMillis

class BungeeSpy : Plugin() {

    companion object {

        lateinit var plugin: Plugin private set
        lateinit var playerDataHandler: PlayerDataHandler private set
        lateinit var message: Message private set

    }

    override fun onEnable() {
        plugin = this

        // 起動処理
        val time = measureTimeMillis {

            message = Message()
            playerDataHandler = PlayerDataHandler()

            // リスナー登録
            proxy.pluginManager.registerListener(this, ChatListener())
            proxy.pluginManager.registerListener(this, PlayerDisconnectListener())
            proxy.pluginManager.registerListener(this, ServerSwitchListener())

            // コマンド登録
            proxy.pluginManager.registerCommand(this, BungeeSpyCommand())

        }

        logger.info("Successfully activated. (${time}ms)")
    }
}
