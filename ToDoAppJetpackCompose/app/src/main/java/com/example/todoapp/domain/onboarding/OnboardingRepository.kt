package com.example.todoapp.domain.onboarding

import com.example.todoapp.domain.onboarding.model.OnboardingItem

interface OnboardingRepository {
    fun getPages(): List<OnboardingItem>
    fun markSeen()
    fun isSeen(): Boolean
}
