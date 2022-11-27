package com.example.playerapp.data

import com.example.playerapp.model.FakePlayerDataSource
import com.example.playerapp.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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