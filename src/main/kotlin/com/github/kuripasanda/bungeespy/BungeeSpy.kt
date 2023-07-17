package com.github.kuripasanda.bungeespy

import net.md_5.bungee.api.plugin.Plugin
import kotlin.system.measureTimeMillis

class BungeeSpy : Plugin() {

    companion object {

        lateinit var plugin: Plugin

    }

    override fun onEnable() {
        plugin = this

        // 起動処理
        val time = measureTimeMillis {

        }

        logger.info("Successfully activated. (${time}ms)")
    }
}
