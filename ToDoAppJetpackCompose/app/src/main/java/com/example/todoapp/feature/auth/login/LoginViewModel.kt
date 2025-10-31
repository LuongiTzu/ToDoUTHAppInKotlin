package com.example.todoapp.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun signInWithGoogleIdToken(
        idToken: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _state.update { it.copy(loading = false) }
                if (task.isSuccessful) {
                    // ✅ Refresh thông tin user ngay sau khi đăng nhập
                    FirebaseAuth.getInstance().currentUser?.reload()
                    onSuccess()
                }
                else onError(task.exception?.localizedMessage ?: "Login failed")
            }
    }
}
