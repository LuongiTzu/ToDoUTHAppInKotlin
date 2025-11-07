package com.example.todoapp.ui.navigation

sealed class Dest(val route: String) {

    // --- Khởi động ---
    data object Splash : Dest("splash")
    data object Onboarding : Dest("onboarding")

    // --- Xác thực / Hồ sơ ---
    data object Login : Dest("auth/login")
    data object Profile : Dest("profile")

    // --- Task Flow (Trang chủ + Chi tiết) ---
    data object TaskList : Dest("tasks")                    // danh sách công việc
    data object TaskDetail : Dest("taskDetail/{id}")        // chi tiết công việc
}
