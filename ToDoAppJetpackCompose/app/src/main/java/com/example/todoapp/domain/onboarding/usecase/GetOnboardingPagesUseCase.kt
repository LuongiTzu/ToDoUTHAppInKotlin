package com.example.todoapp.domain.onboarding.usecase

import com.example.todoapp.domain.onboarding.OnboardingRepository
import com.example.todoapp.domain.onboarding.model.OnboardingItem

class GetOnboardingPagesUseCase(
    private val repo: OnboardingRepository
) {
    operator fun invoke(): List<OnboardingItem> = repo.getPages()
}
