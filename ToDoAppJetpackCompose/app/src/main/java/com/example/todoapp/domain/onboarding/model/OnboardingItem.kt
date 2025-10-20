package com.example.todoapp.domain.onboarding.model

data class OnboardingItem(
    val title: String,
    val body: String,
    val imageRes: Int   // với demo ta dùng resource id; sau này có thể đổi sang url
)
