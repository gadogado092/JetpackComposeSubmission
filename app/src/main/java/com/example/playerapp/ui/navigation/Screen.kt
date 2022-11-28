package com.example.playerapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("home/{playerId}") {
        fun createRoute(playerId: Int) = "home/$playerId"
    }
}