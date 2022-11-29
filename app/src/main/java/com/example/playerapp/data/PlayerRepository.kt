package com.example.playerapp.data

import com.example.playerapp.model.FakePlayerDataSource
import com.example.playerapp.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PlayerRepository {

    private val players = mutableListOf<Player>()

    init {
        if (players.isEmpty()) {
            FakePlayerDataSource.dummyPlayers.forEach {
                players.add(it)
            }
        }
    }

    fun getAllPlayer(): Flow<List<Player>> {
        return flowOf(players)
    }

    fun getFavoritePlayers(): Flow<List<Player>> {
        return getAllPlayer()
            .map { players ->
                players.filter { player ->
                    player.isFavourite
                }
            }
    }

    fun getPlayerById(playerId: Int): Player {
        return players.first {
            it.id == playerId
        }
    }

    fun updateStatusFavorite(playerId: Int, status: Boolean): Flow<Boolean> {
        val index = players.indexOfFirst { it.id == playerId }
        val result = if (index >= 0) {
            val player = players[index]
            players[index] =
                player.copy(isFavourite = status)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: PlayerRepository? = null

        fun getInstance(): PlayerRepository =
            instance ?: synchronized(this) {
                PlayerRepository().apply {
                    instance = this
                }
            }
    }

}