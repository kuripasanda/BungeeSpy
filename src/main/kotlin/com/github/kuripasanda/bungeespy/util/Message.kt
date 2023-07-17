package com.github.kuripasanda.bungeespy.util

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer

class Message {

    var prefix = getColored("&6[BungeeSpy]")
        set(prefix) { field = getColored(prefix) }

    fun getColored(message: String): String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }

    fun getBaseComponent(message: String): BaseComponent {
        return TextComponent(message)
    }


    fun sendMessage(sender: CommandSender, needPrefix: Boolean, message: String) {
        sendMessage(sender as ProxiedPlayer, needPrefix, message)
    }
    fun sendMessage(player: ProxiedPlayer, needPrefix: Boolean, message: String) {
        var prefix_ = ""
        if (needPrefix) prefix_ = "$prefix "
        player.sendMessage(getBaseComponent(getColored("$prefix_&f$message")))
    }

}