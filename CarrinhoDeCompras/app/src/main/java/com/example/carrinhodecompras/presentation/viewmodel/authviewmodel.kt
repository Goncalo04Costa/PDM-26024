package com.example.carrinhodecompras.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrinhodecompras.classes.User
import com.example.carrinhodecompras.repositorio.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun register(email: String, password: String) = viewModelScope.launch {
        _authState.value = _authState.value.copy(isLoading = true, error = null)
        val user = authRepository.registerUser(email, password)
        _authState.value = if (user != null) {
            AuthState(user = User(user.uid, user.displayName ?: "", user.email ?: ""))
        } else {
            AuthState(error = "Falha ao registrar usuário.")
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _authState.value = _authState.value.copy(isLoading = true, error = null)
        val user = authRepository.login(email, password)
        _authState.value = if (user != null) {
            AuthState(user = User(user.uid, user.displayName ?: "", user.email ?: ""))
        } else {
            AuthState(error = "Falha no login. Verifique suas credenciais.")
        }
    }

    fun logout() {
        authRepository.logout()
        _authState.value = AuthState()
    }
}
