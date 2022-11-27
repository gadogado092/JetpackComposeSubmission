package com.example.playerapp.di

import com.example.playerapp.data.PlayerRepository

object Injection {

    fun provideRepository(): PlayerRepository {
        return PlayerRepository.getInstance()
    }

}