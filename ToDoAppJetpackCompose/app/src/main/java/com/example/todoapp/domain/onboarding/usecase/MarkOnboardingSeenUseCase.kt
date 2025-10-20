package com.example.todoapp.domain.onboarding.usecase

import com.example.todoapp.domain.onboarding.OnboardingRepository

class MarkOnboardingSeenUseCase(
    private val repo: OnboardingRepository
) {
    operator fun invoke() = repo.markSeen()
}
