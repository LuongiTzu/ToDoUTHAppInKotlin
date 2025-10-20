package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.feature.onboarding.OnboardingScreen
import com.example.todoapp.feature.onboarding.SplashScreen

@Composable
fun AppNavGraph(nav: NavHostController) {
    NavHost(navController = nav, startDestination = Dest.Splash.route) {
        composable(Dest.Splash.route) {
            SplashScreen(onDone = {
                nav.navigate(Dest.Onboarding.route) { popUpTo(0) }
            })
        }
        composable(Dest.Onboarding.route) {
            OnboardingScreen(onFinished = {
                // TODO: đi đến Login/Home khi làm tiếp
            })
        }
    }
}
