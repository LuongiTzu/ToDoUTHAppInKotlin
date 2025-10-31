package com.example.todoapp.domain.auth.usecase

import com.example.todoapp.domain.auth.AuthRepository

class SignInEmailPasswordUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        // chừa sẵn nếu sau bạn muốn email/password
        return Result.failure(UnsupportedOperationException("Not implemented"))
    }
}
