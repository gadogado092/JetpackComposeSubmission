package com.example.playerapp.model

data class Player(
    val id: String,
    val name: String,
    val position: String,
    val photoUrl: String,
    val isFavourite: Boolean = false
)
