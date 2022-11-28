package com.example.playerapp.model

data class Player(
    val id: Int,
    val name: String,
    val birthDate: String,
    val tall: String,
    val position: String,
    val club: String,
    val country: String,
    val photoUrl: String,
    val isFavourite: Boolean = false
)
