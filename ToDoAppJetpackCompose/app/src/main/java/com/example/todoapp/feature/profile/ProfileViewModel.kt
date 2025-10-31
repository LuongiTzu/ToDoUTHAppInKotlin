package com.example.todoapp.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.profile.ProfileRepositoryFake
import com.example.todoapp.domain.profile.ProfileRepository
import com.example.todoapp.domain.profile.model.UserProfile
import com.example.todoapp.domain.profile.usecase.GetProfileUseCase
import com.example.todoapp.domain.profile.usecase.UpdateProfileUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val loading: Boolean = true,
    val profile: UserProfile? = null,
    val error: String? = null
)

class ProfileViewModel(
    private val getProfile: GetProfileUseCase,
    private val updateProfile: UpdateProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val authListener = FirebaseAuth.AuthStateListener {
        // mỗi lần user đổi / login xong → load lại
        load()
    }

    init {
        auth.addAuthStateListener(authListener)
        load()
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authListener)
    }

    private fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }

        // local: dữ liệu user đã lưu (DOB/ảnh tùy chỉnh)
        val local = getProfile()

        // firebase: dữ liệu account Google
        val fUser = auth.currentUser
        val fromFirebase = if (fUser != null) {
            UserProfile(
                name = fUser.displayName.orEmpty(),
                email = fUser.email.orEmpty(),
                dob = local?.dob.orEmpty(),            // Google không có DOB
                avatarUrl = fUser.photoUrl?.toString(),
                avatarRes = local?.avatarRes
            )
        } else null

        // Ưu tiên firebase; giữ lại DOB/ảnh local nếu người dùng từng chỉnh
        val merged = when {
            fromFirebase != null && local != null -> fromFirebase.copy(
                dob = if (local.dob.isNotBlank()) local.dob else fromFirebase.dob,
                avatarRes = local.avatarRes ?: fromFirebase.avatarRes
            )
            fromFirebase != null -> fromFirebase
            local != null -> local
            else -> UserProfile()
        }

        _state.update { it.copy(loading = false, profile = merged) }
    }

    fun save(profile: UserProfile, onDone: () -> Unit) = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }
        updateProfile(profile)
            .onSuccess { onDone() }
            .onFailure { e -> _state.update { it.copy(error = e.message) } }
        _state.update { it.copy(loading = false) }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo: ProfileRepository = ProfileRepositoryFake()
                return ProfileViewModel(
                    getProfile = GetProfileUseCase(repo),
                    updateProfile = UpdateProfileUseCase(repo)
                ) as T
            }
        }
    }
}
