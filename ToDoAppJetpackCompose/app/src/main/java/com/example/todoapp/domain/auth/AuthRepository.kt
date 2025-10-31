package com.example.todoapp.domain.auth

interface AuthRepository {
    /** Đăng nhập Google (demo): thành công -> Unit; lỗi -> message */
    suspend fun signInWithGoogle(): Result<Unit>
    suspend fun signOut(): Result<Unit>
}
