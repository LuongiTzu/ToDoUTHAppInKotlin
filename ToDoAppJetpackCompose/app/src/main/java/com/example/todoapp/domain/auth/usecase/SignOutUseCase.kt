package com.example.todoapp.domain.auth.usecase

import com.example.todoapp.domain.auth.AuthRepository

class SignOutUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> = repo.signOut()
}
