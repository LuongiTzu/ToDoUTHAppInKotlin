package com.example.todoapp.data.auth

import com.example.todoapp.domain.auth.AuthRepository
import kotlinx.coroutines.delay

class FakeAuthRepository : AuthRepository {
    override suspend fun signInWithGoogle(): Result<Unit> {
        delay(600) // giả lập network
        return Result.success(Unit)
    }
    override suspend fun signOut(): Result<Unit> = Result.success(Unit)
}
