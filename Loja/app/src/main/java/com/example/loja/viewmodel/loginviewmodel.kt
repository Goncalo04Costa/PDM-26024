package com.example.loja.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    var erroMensagem = mutableStateOf("")
        private set

    private val auth = FirebaseAuth.getInstance()

    fun loginUsuario(email: String, senha: String) {
        if (email.isBlank() || senha.isBlank()) {
            erroMensagem.value = "Preencha todos os campos."
            return
        }

        // Autenticação no Firebase
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    erroMensagem.value = "Login bem-sucedido!"
                } else {
                    erroMensagem.value = "Erro no login: ${task.exception?.message}"
                }
            }
    }
}
