package com.example.todoapp.data.auth

import com.example.todoapp.domain.auth.AuthRepository

// Khi sẵn sàng, bạn inject FirebaseAuth và Google Sign-In vào đây
class FirebaseAuthRepository : AuthRepository {
    override suspend fun signInWithGoogle(): Result<Unit> =
        Result.failure(UnsupportedOperationException("Implement with Firebase later"))

    override suspend fun signOut(): Result<Unit> = Result.success(Unit)
}
