package com.example.todoapp.domain.profile.usecase

import com.example.todoapp.domain.profile.ProfileRepository
import com.example.todoapp.domain.profile.model.UserProfile

class UpdateProfileUseCase(private val repo: ProfileRepository) {
    suspend operator fun invoke(profile: UserProfile): Result<Unit> =
        repo.updateProfile(profile)
}
