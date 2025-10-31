package com.example.todoapp.domain.profile.usecase

import com.example.todoapp.domain.profile.ProfileRepository
import com.example.todoapp.domain.profile.model.UserProfile

class GetProfileUseCase(private val repo: ProfileRepository) {
    suspend operator fun invoke(): UserProfile = repo.getProfile()
}
