package com.example.todoapp.domain.profile

import com.example.todoapp.domain.profile.model.UserProfile

interface ProfileRepository {
    suspend fun getProfile(): UserProfile
    suspend fun updateProfile(profile: UserProfile): Result<Unit>
}
