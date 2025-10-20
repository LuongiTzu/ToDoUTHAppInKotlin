package com.example.todoapp.ui.navigation

sealed class Dest(val route: String) {
    data object Splash : Dest("splash")
    data object Onboarding : Dest("onboarding")
}
