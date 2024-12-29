package com.example.carrinhodecompras.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MenuUtilizadorViewModel: ViewModel(){
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun signIn(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Log.d("MenuUtilizadorViewModel", "Resultado: ${result}")
                onResult(true)
            } catch (e: Exception) {
                Log.d("MenuUtilizadorViewModel", "Não funcionei excecao:${e}")
                onResult(false)
            }
        }
    }

    fun signOut() {
        try {
            auth.signOut()
        } catch (e: Exception) {
            Log.d("MenuUtilizadorViewModel", "Não funcionei excecao:${e}")
        }
    }
}
