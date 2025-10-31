package com.example.todoapp.domain.profile.model

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val dob: String = "",
    val avatarUrl: String? = null,   // <-- thêm
    val avatarRes: Int? = null       // giữ để dùng ảnh local nếu cần
)
