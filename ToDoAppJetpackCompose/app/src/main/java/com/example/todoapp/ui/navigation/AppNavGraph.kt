package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.feature.auth.login.LoginScreen
import com.example.todoapp.feature.onboarding.OnboardingScreen
import com.example.todoapp.feature.onboarding.SplashScreen
import com.example.todoapp.feature.profile.ProfileScreen

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
                nav.navigate(Dest.Login.route) { popUpTo(0) }
            })
        }

        composable(Dest.Login.route) {
            LoginScreen(
                onSignedIn = {
                    nav.navigate(Dest.Profile.route)
                }
            )
        }

        composable(Dest.Profile.route) {
            ProfileScreen(
                onBack = { nav.popBackStack() } // ← quay lại Login
            )
        }
    }
}
