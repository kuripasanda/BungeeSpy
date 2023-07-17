package com.github.kuripasanda.bungeespy.util

import com.github.kuripasanda.bungeespy.data.PlayerData
import java.util.UUID

class PlayerDataHandler {

    private val playerDatas = HashMap<UUID, PlayerData>()
    val monitorServerMovePlayerUUIDs = mutableListOf<UUID>()

    fun hasPlayerData(uuid: UUID): Boolean {
        return playerDatas.containsKey(uuid)
    }

    fun getPlayerData(uuid: UUID): PlayerData? {
        if (!playerDatas.containsKey(uuid)) return null
        return playerDatas[uuid]
    }

    fun setPlayerData(uuid: UUID, playerData: PlayerData) {
        playerDatas[uuid] = playerData
    }

    fun removePlayerData(uuid: UUID): PlayerData? {
        if (!playerDatas.containsKey(uuid)) return null
        val playerData = playerDatas[uuid]
        playerDatas.remove(uuid)
        return playerData
    }


    fun getPlayersHasMonitorServer(serverName: String): List<UUID> {
        val players = mutableListOf<UUID>()
        playerDatas.forEach { (uuid, playerData) ->
            if (playerData.monitorServers.contains(serverName)) players.add(uuid)
        }
        return players
    }
    fun getSpyServers(uuid: UUID): MutableList<String> {
        if (!hasPlayerData(uuid)) return mutableListOf()
        return getPlayerData(uuid)!!.monitorServers
    }
    fun addSpyServer(uuid: UUID, serverName: String): PlayerData {
        var playerData = PlayerData()
        if (hasPlayerData(uuid)) playerData = getPlayerData(uuid)!!
        if (!playerData.monitorServers.contains(serverName)) playerData.monitorServers.add(serverName)
        setPlayerData(uuid, playerData)
        return playerData
    }
    fun addSpyServers(uuid: UUID, serverNames: List<String>): PlayerData {
        var playerData = PlayerData()
        if (hasPlayerData(uuid)) playerData = getPlayerData(uuid)!!
        playerData.monitorServers.addAll(serverNames)
        setPlayerData(uuid, playerData)
        return playerData
    }

    fun removeSpyServer(uuid: UUID, serverName: String): PlayerData {
        var playerData = PlayerData()
        if (hasPlayerData(uuid)) playerData = getPlayerData(uuid)!!
        if (playerData.monitorServers.contains(serverName)) playerData.monitorServers.remove(serverName)
        setPlayerData(uuid, playerData)
        return playerData
    }
    fun removeSpyServers(uuid: UUID, serverNames: List<String>): PlayerData {
        var playerData = PlayerData()
        if (hasPlayerData(uuid)) playerData = getPlayerData(uuid)!!
        playerData.monitorServers.removeAll(serverNames)
        setPlayerData(uuid, playerData)
        return playerData
    }

}