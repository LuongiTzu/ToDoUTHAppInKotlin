package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.feature.auth.login.LoginScreen
import com.example.todoapp.feature.onboarding.OnboardingScreen
import com.example.todoapp.feature.onboarding.SplashScreen
import com.example.todoapp.feature.profile.ProfileScreen
import com.example.todoapp.feature.task.TaskListScreen
import com.example.todoapp.feature.task.TaskDetailScreen

@Composable
fun AppNavGraph(nav: NavHostController) {
    NavHost(navController = nav, startDestination = Dest.Splash.route) {

        composable(Dest.Splash.route) {
            SplashScreen(
                navController = nav,
                onDone = {
                    nav.navigate(Dest.Onboarding.route) { popUpTo(0) }
                }
            )
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
                onLogout = {
                    nav.navigate(Dest.Login.route) { popUpTo(0) }
                },
                onSaveDone = {
                    nav.navigate(Dest.TaskList.route) { popUpTo(0) }
                }
            )
        }

        composable(Dest.TaskList.route) {
            TaskListScreen(navController = nav)
        }

        composable(Dest.TaskDetail.route) { backStack ->
            val id = backStack.arguments?.getString("id")?.toIntOrNull() ?: 0
            TaskDetailScreen(taskId = id, navController = nav)
        }
    }
}
