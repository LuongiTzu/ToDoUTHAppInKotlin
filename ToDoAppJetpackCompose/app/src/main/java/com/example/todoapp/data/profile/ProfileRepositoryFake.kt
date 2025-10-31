package com.example.todoapp.data.profile

import com.example.todoapp.R
import com.example.todoapp.domain.profile.ProfileRepository
import com.example.todoapp.domain.profile.model.UserProfile

class ProfileRepositoryFake : ProfileRepository {

    private var cache = UserProfile(
        name = "",
        email = "",
        dob = "",
        avatarRes = R.drawable.ic_launcher_foreground // placeholder
    )

    override suspend fun getProfile(): UserProfile = cache

    override suspend fun updateProfile(profile: UserProfile): Result<Unit> {
        cache = profile
        return Result.success(Unit)
    }
}
